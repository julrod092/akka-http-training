package com.example.users.sevices

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.example.users.persistence.UserRepository
import com.example.users.services.UserRoute
import org.scalatest.{Matchers, WordSpec}


class UserServiceTest extends WordSpec with Matchers with ScalatestRouteTest {

  val userRoute = new UserRoute(UserRepository)

  "User service" should {

    "verify if server is running" in {
      Get("/user") ~> userRoute.route ~> check {
        status === StatusCodes.OK
        responseAs[String] shouldEqual "OK"
      }
    }

    "Reject if other route is given" in {
      Get("/us") ~> userRoute.route ~> check {
        handled
      }
    }

    "Verify if a user exist, if not, then return nothing" in {
      Get("/user/julrod0921") ~> userRoute.route ~> check {
        responseAs[String] shouldEqual ""
      }
    }

      "Verify if a user exist, if exist, return the user data" in {
      Get("/user/julrod092") ~> userRoute.route ~> check {
        responseAs[String] shouldEqual """{"id":"5d50893c-0995-11e7-93ae-92361f002671","name":"julrod092","lastName":"Julian","userName":"Rodriguez"}"""
      }
    }
  }
}


