package com.example

import java.util.UUID

import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import com.example.entity.User
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import io.circe.syntax._

class Routes extends Directives {

  val route: Route =
    path("user") {
      get {
        complete(HttpResponse(StatusCodes.OK, entity = User(UUID.randomUUID(), "julrod092", "Julian", "Rodriguez").asJson.toString))
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
          complete(StatusCodes.OK)
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
