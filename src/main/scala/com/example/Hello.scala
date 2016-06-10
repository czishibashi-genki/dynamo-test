package com.example

import java.util.concurrent.ThreadLocalRandom

import akka.actor.{Actor, ActorSystem, Props}

import scala.annotation.tailrec
import scala.util.{Random, Try}

//import javax.crypto.Mac
//import javax.crypto.spec.SecretKeySpec
//
//import awscala._
//import dynamodbv2._

object ApiMode{
  sealed abstract class Mode(val code: Int)
  case object READ extends Mode(1)
  case object CONSISTENT_READ extends Mode(2)
  case object WRITE extends Mode(3)
}

object Hello {
  def main(args: Array[String]): Unit = {
    if (args.size == 0) showUsage()

    type ArgsMap = Map[Symbol, Any]
    val cardinality = 10000

    @tailrec
    def nextOption(map: ArgsMap, args: List[String]): ArgsMap = {
      args match {
        case Nil => map
        case ("-r"  | "--read") :: tail => nextOption(map ++ Map('isRead -> true), tail)
        case ("-cr" | "--consistent-read") :: tail => nextOption(map ++ Map('isConsistentRead -> true), tail)
        case ("-w"  | "--write") :: tail => nextOption(map ++ Map('isWrite -> true), tail)
        case ("-c") :: option :: tail => nextOption(map ++ Map('concurrency -> option), tail) // トータルリクエスト数
        case ("-i" | "--interval") :: option :: tail => nextOption(map ++ Map('interval -> option), tail)
        case ("-repeat") :: option :: tail => nextOption(map ++ Map('repeat -> option), tail)
      }
    }

    val optionMap = nextOption(Map.empty[Symbol, Any], args.toList)

    val mode = if (optionMap.get('isRead) == Some(true)) ApiMode.READ
      else if (optionMap.get('isConsistentRead) == Some(true)) ApiMode.CONSISTENT_READ
      else if(optionMap.get('isWrite) == Some(true)) ApiMode.WRITE
      else -1

    (optionMap.get('concurrency), optionMap.get('interval), optionMap.get('repeat)) match {
      case(Some(con), Some(interval), Some(repeat)) =>
        println(s"同時接続数: $con, リクエスト間隔: ${interval}, リピート回数: $repeat")
        (1 to repeat.asInstanceOf[String].toInt).foreach{ i =>
          (1 to con.asInstanceOf[String].toInt).par.foreach{ c =>
            Try {
              val key = "install_history#" + ThreadLocalRandom.current().nextInt(cardinality)
              mode match {
                case ApiMode.READ => DynamoDBClient.get(key)
                case ApiMode.CONSISTENT_READ => DynamoDBClient.consistentGet(key)
                case ApiMode.WRITE => DynamoDBClient.put(key, ("value", "2016-03-08 20:08:00#1,2016-03-08 20:09:42#1,2016-03-09 10:24:46#1,2016-03-09 11:10:46#1,"))
              }
            }.getOrElse(println("error occured"))
          }
          Thread.sleep(interval.asInstanceOf[String].toInt)
        }
      case _ => showUsage
    }
  }


  def showUsage() = println(
        """usage
          |java -jar ***.jar [-r | --read] or [-cr | --consited-read] or [-w | --write]
          |                  [-c ]
          |
          |These are commands used in various situations:
          |
          |read             Reading item with eventual consistency
          |consistent-read  Reading item with consistent-read
          |write            write item
          |Options:
          |  -c Concurrency
          |  -repeat
          |  -i Interval[ms]
        """.stripMargin)
}
