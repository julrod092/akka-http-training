package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.example.users.persistence.database.ProductionDatabase
import com.example.users.services.Routes
import scala.concurrent.duration._

import scala.io.StdIn

object WebServer extends App with ProductionDatabase {

  // needed to run the route
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val userRoute = new Routes
  database.create(5 seconds)

  val bindingFuture = Http().bindAndHandle(userRoute.route, "localhost", 8080)
  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return

  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ ⇒ system.terminate()) // and shutdown when done
}