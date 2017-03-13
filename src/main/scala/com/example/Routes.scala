package com.example

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import spray.json._

final case class User (name: String)

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat1(User)
}

class Routes extends Directives with JsonSupport {

  val route: Route =
    path("user") {
      get {
        complete(User("Julian"))
      } ~
      post {
        entity(as[User]) { user =>
          println(s"User created: ${user.name}")
          complete(StatusCodes.OK)
        }
      } ~
      put {
        entity(as[User]){ user =>
          println(s"User updated: ${user.name}")
          complete(StatusCodes.OK)---
        }
      } ~
      delete {
        entity(as[User]){ user =>
          println(s"User deleted: ${user.name}")
          complete(StatusCodes.OK)
        }
      }
    }
}
