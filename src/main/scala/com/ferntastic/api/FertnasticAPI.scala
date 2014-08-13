package com.ferntastic.api

import org.scalatra._
import scalate.ScalateSupport

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

// HTTP libraries
import scalaj.http.Http

class FerntasticAPI extends FerntasticAPIStack with JacksonJsonSupport {

	// Sets up automatic case class to JSON output serialization, required by
  	// the JValueResult trait.
  	protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

	get("/") {
		FlowerData.all
	}

	get("/github/users/:id") {
		GithubRequest.getUser(params("id"))
	}  
	// A Flower object to use as a faked-out data model
	case class Flower(slug: String, name: String)

	object GithubRequest {

		def getUser(id: String) : String = {

			var url = "https://api.github.com/users/" + id
			return Http(url).asString

		}

	}

	object FlowerData {

		/**
		* Some fake flowers data so we can simulate retrievals.
		*/
		var all = List(
			Flower("yellow-tulip", "Yellow Tulip"),
			Flower("red-rose", "Red Rose"),
			Flower("black-rose", "Black Rose"))

	}

	// Before every action runs, set the content type to be in JSON format.
	before() {
		contentType = formats("json")
	}

}


