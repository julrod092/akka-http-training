package com.example.users.services

import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import com.example.infrastructure.dto.UserDTO
import com.example.users.persistence.UserRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContextExecutor

class UserRoute(userRepository: UserRepository)(implicit private val ec: ExecutionContextExecutor) extends Directives {

  val route: Route =
    path("user" / Segment) { username =>
      get {
        complete(userRepository.fetch(username))
      } ~
      delete {
        complete(userRepository.delete(username).map(future =>
          HttpResponse(OK, entity = s"User deleted: $future")
        ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
      }
    } ~
    path("user") {
      post {
        entity(as[UserDTO]) { user =>
          complete(userRepository.add(user).map(future =>
            HttpResponse(OK, entity = s"User created: ${future.userName}")
          ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
        }
      } ~
      put {
        entity(as[UserDTO]) { user =>
          complete(userRepository.update(user).map(future =>
            HttpResponse(OK, entity = s"User updated: ${future.userName}")
          ).recover{case e: Exception => HttpResponse(BadRequest, entity = e.toString)})
        }
      } ~
      get {
        complete(StatusCodes.OK)
      }
  }
}
