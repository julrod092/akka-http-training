package com.example.users.services

import akka.http.scaladsl.model.StatusCodes
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
          complete(userRepository.delete(username).map(future => (StatusCodes.OK, s"User deleted: $future"))
            .recover { case e: Exception => (StatusCodes.InternalServerError, e) })
        }
    } ~
      path("user") {
        post {
          entity(as[UserDTO]) { user =>
            complete(userRepository.add(user).map(future => (StatusCodes.OK, s"User created: ${future.userName}"))
              .recover { case e: Exception => (StatusCodes.InternalServerError, e) })
          }
        } ~
          put {
            entity(as[UserDTO]) { user =>
              complete(userRepository.update(user).map(future => (StatusCodes.OK, s"User updated: ${future.userName}"))
                .recover { case e: Exception => (StatusCodes.InternalServerError, e) })
            }
          } ~
          get {
            complete(StatusCodes.OK)
          }
      }
}
