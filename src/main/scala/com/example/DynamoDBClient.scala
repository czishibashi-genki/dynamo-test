package com.example


import awscala._
import awscala.dynamodbv2._

object DynamoDBClient {
  val TABLE_NAME = "fox2-dynamodb-test01"
  implicit val region = Region.AP_NORTHEAST_1
  implicit val db = DynamoDB("XXXXXXXXXXXXXXXX", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")

  def put(hashPK: Any, attriubtes: (String, Any)*) =
    db.table(TABLE_NAME).getOrElse(throw new Exception)
      .put(hashPK, attriubtes:_*)


  def get(hashPK: Any) =
    db.table(TABLE_NAME).getOrElse(throw new Exception)
      .get(hashPK)


  def consistentGet(hashPK: Any) =
    db.consistentRead(true)
      .table(TABLE_NAME).getOrElse(throw new Exception)
      .get(hashPK)
}
