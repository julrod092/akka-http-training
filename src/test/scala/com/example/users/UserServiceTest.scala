package com.example.users

import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
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
        responseAs[String] shouldEqual """{"id":"5d50893c-0995-11e7-93ae-92361f002671","name":"Julian","lastName":"Rodriguez","userName":"julrod092"}"""
      }
    }

    "Allow insert a user" in {

      val jsonRequest = ByteString(
        """{"id": "67856d17-2724-49b0-ad76-f6ccc6390b58","userName": "julrod092","name": "Julian","lastName": "Rodriguez"}""".stripMargin
      )

      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/user",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> userRoute.route ~> check {
        responseAs[String] shouldEqual "User created: julrod092"
      }
    }

    "Allow update a user" in {

      val jsonRequest = ByteString(
        """{"id": "67856d17-2724-49b0-ad76-f6ccc6390b58","userName": "julrod092","name": "Julian","lastName": "Rodriguez"}""".stripMargin
      )

      val postRequest = HttpRequest(
        HttpMethods.PUT,
        uri = "/user",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest))

      postRequest ~> userRoute.route ~> check {
        responseAs[String] shouldEqual "User updated: julrod092"
      }
    }

    "Allow delete a user" in {
      Delete("/user/julrod092") ~> userRoute.route ~> check {
        responseAs[String] shouldEqual "User deleted: julrod092"
      }
    }
  }
}


