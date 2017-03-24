package com.example.users.services

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer
import com.example.infrastructure.dto.UserDTO
import com.example.infrastructure.kafka.KafkaProducer
import com.example.users.persistence.UserRepository
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._

import scala.concurrent.ExecutionContextExecutor

class UserRoute(userRepository: UserRepository)(implicit ec: ExecutionContextExecutor, system: ActorSystem, materializer: ActorMaterializer) extends Directives {

  val kafka = new KafkaProducer
  val route: Route =
    path("user" / Segment) { userName =>
      get {
        complete(userRepository.fetch(userName))
      } ~
        delete {
          complete(userRepository.delete(userName).map(future => (StatusCodes.OK, kafka.sendMessage(userName, "deleted")))
            .recover { case e => (StatusCodes.PartialContent, e.getMessage) })
        }
    } ~
    path("user") {
      post {
        entity(as[UserDTO]) { user =>
          complete(userRepository.add(user).map(future => (StatusCodes.OK, kafka.sendMessage(future.userName, "created")))
            .recover { case e => (StatusCodes.PartialContent, e.getMessage) })
        }
      } ~
      put {
        entity(as[UserDTO]) { user =>
          complete(userRepository.update(user).map(future => (StatusCodes.OK, kafka.sendMessage(future.userName, "updated")))
            .recover { case e => (StatusCodes.PartialContent, e.getMessage) })
        }
      } ~
      get {
        complete(StatusCodes.OK)
      }
    }
}
