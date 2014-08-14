package com.ferntastic.api

import org.scalatra._
import scalate.ScalateSupport

// JSON-related libraries
import org.json4s.{DefaultFormats, Formats}

// JSON handling support from Scalatra
import org.scalatra.json._

// HTTP libraries
import scalaj.http.Http

import com.mongodb.casbah.Imports._


import com.ferntastic.api.service.MongoDB

class FerntasticAPI(db: MongoDB) extends FerntasticAPIStack with JacksonJsonSupport {

	// Sets up automatic case class to JSON output serialization, required by
  	// the JValueResult trait.
  	protected implicit val jsonFormats: Formats = DefaultFormats.withBigDecimal

  	get("/") {
  		db.collection.find()
  		for  {x <- db.collection } yield x.toString()
  	}

	get("/github/users/:id") {
		GithubRequest.getUser(params("id"))
	}  

	get("/seed") {
		db.create
	}

	get("/benchmark") {
		"hey"
	}

	get("/flowers") {
		contentType="text/html"
		var flowerNames = for{flower: Flower <- FlowerData.all} yield flower.name
		jade("hello-scalate", "flowers" -> flowerNames.mkString(", "))
	}

	object GithubRequest {

		def getUser(id: String) : String = {

			var url = "https://api.github.com/users/" + id
			return Http(url).asString

		}

	}

	// A Flower object to use as a faked-out data model
	case class Flower(slug: String, name: String)
	object FlowerData {

		/**
		* Some fake flowers data so we can simulate retrievals.
		*/
		var all = List(
			Flower("yellow-tulip", "Yellow Tulip"),
			Flower("red-rose", "Red Rose"),
			Flower("black-rose", "Black Rose"),
			Flower("daisy", "Daisy"),
			Flower("japetto", "Pinocchio"))

	}



	// Before every action runs, set the content type to be in JSON format.
	before() {
		contentType = formats("json")
	}

}


