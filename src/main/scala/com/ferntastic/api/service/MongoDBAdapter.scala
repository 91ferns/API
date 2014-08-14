package com.ferntastic.api.service

// JSON handling support from Scalatra
import org.scalatra.json._
import com.mongodb.casbah.Imports._

class MongoDBAdapter {
	
	val MongoConnectionUrl = sys.env.get("MONGOHQ_URL").getOrElse("")

	def getConnection(fallback: String): MongoDB = MongoConnectionUrl match {
		case connstring: String if connstring != "" => {
			val uri = MongoClientURI(connstring)
			MongoClient(uri)(uri.database.getOrElse(fallback))
		}
		case _ => MongoClient()(fallback)
	}



	val db = getConnection("test")
	val collection = db("inventory")

	def create {
		val builder = collection.initializeOrderedBulkOperation
		builder.insert(MongoDBObject("type" -> "misc", "item" -> "awesome card", "qty" -> 1))
		builder.insert(MongoDBObject("type" -> "misc", "item" -> "less awesome card", "qty" -> 3))
		builder.insert(MongoDBObject("type" -> "misc", "item" -> "super awesome card", "qty" -> 7))
		builder.insert(MongoDBObject("type" -> "misc", "item" -> "great card", "qty" -> 5))
		builder.insert(MongoDBObject("type" -> "misc", "item" -> "mastercard", "qty" -> 2))


		builder.execute()
	}


}