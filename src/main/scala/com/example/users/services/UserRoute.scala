package com.example.users.services

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import com.example.infrastructure.dto.UserDTO
import com.example.users.persistence.{Cassandra, ProductionDatabase}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

class UserRoute extends Directives with ProductionDatabase with Cassandra.cassandraConnector.Connector {

  val route: Route =
    path("user" / Segment) { username =>
      get {
        complete(UserRepository.fetch(username))
      } ~
        delete {
          //UserRepository.delete(username)
          complete(StatusCodes.OK)
        }
    } ~
      path("user") {
        post {
          entity(as[UserDTO]) { user =>
            //UserRepository.addOrUpdate(user)
            complete(StatusCodes.OK)
          }
        } ~
          put {
            entity(as[UserDTO]) { user =>
              //UserRepository.addOrUpdate(user)
              complete(user)
            }
          }
      }
}
