package com.example.infrastructure

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.infrastructure.database.ProductionDatabase
import com.example.users.persistence.UserRepository
import com.example.users.services.UserRoute

import scala.concurrent.duration._
import scala.io.StdIn

object WebServer extends App with ProductionDatabase {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val userRoute = new UserRoute(UserRepository)

  database.create(5.seconds)

  val bindingFuture = Http().bindAndHandle(userRoute.route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine()

  bindingFuture
    .flatMap(_.unbind())
    .onComplete(_ ⇒ system.terminate())
}