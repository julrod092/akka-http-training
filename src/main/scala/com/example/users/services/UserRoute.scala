package com.example.users.services

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.{Directives, Route}
import com.example.infrastructure.dto.UserDTO
import com.example.users.persistence.UserRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContextExecutor

class UserRoute (implicit val ec: ExecutionContextExecutor) extends Directives {

  val route: Route =
    path("user" / Segment) { username =>
      get {
        complete(UserRepository.fetch(username).map(future =>
          HttpResponse(OK, entity = s"User created: $username")
        ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
      } ~
      delete {
        complete(UserRepository.delete(username).map(future =>
          HttpResponse(OK, entity = s"User deleted: $username")
        ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
      }
    } ~
    path("user") {
      post {
        entity(as[UserDTO]) { user =>
          complete(UserRepository.add(user).map(future =>
            HttpResponse(OK, entity = s"User created: ${user.userName}")
          ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
        }
      } ~
      put {
        entity(as[UserDTO]) { user =>
          complete(UserRepository.update(user).map(future =>
            HttpResponse(OK, entity = s"User updated: ${user.userName}")
          ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
        }
      } ~
      get {
        complete(OK)
      }
  }
}
