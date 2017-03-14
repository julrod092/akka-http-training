package com.example.users.services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.example.users.persistence.connector.Cassandra
import com.example.users.persistence.database.ProductionDatabase
import com.example.users.persistence.entity.User
import com.example.users.persistence.service.UserService
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class Routes extends Directives with ProductionDatabase with Cassandra.connector.Connector {

  val route: Route =
    path("user") {
      pathPrefix(Segment) { id =>
        get {
          complete(id)
        } ~
          delete {
            entity(as[User]) { user =>
              println(s"User deleted: ${user.name}")
              complete(StatusCodes.OK)
            }
          }
      } ~
        post {
          entity(as[User]) { user =>
            UserService.storeUser(user)
            complete(StatusCodes.OK)
          }
        } ~
        put {
          entity(as[User]) { user =>
            println(s"User updated: ${user.name}")
            complete(StatusCodes.OK)
          }
        }
    }
}
