import org.scalatest._
//import org.scalatest.words.StringVerbBlockRegistration
//
//class DynamoDBClientSpec extends FlatSpec with Matchers {
//  "DinamoDBCilent" should "save special characters" in {
//    val s = "; / ? : @ = & % $ - _ . + ! * ' \" ( ) , { } | \\ ^ ~ [ ] \uD83C\uDF0D"
//    val pk = "test_specify_char"
//    DynamoDBClient.put(pk, "characters" -> s)
//    DynamoDBClient.get(pk).map( item =>
//      item.attributes.headOption.map( h =>
//        h.value.s shouldEqual Some(s)
//      )
//    )
//  }
//
//  "DinamoDBClient" should "save over 400KB String" in {
//    // 1byte文字
//    //    val s = "a" * (4e5 + 9589).toInt // NG
//    //    val s = "a" * (4e5 + 9588).toInt // OK
//    // 2byte文字
//    //    val s = "§" * (2e5 + 4795).toInt // NG
//    //    val s = "§" * (2e5 + 4794).toInt // OK
//    // 3byte文字
//    //    val s = "あ" * 136530 // NG
//    val s = "あ" * 136529 // OK
//
//    val pk = "test_over_400KB_string"
//    DynamoDBClient.put("pk", "string" -> s)
//
//    DynamoDBClient.get(pk).map( item =>
//      item.attributes.headOption.map( h =>
//        h.value.s shouldEqual Some(s)
//      )
//    )
//  }
//}
