package com.example.users

import java.util.UUID

import akka.actor.ActorSystem
import com.example.infrastructure.database.{Cassandra, EmbeddedDatabase}
import com.example.infrastructure.dto.UserDTO
import com.example.users.util.CassandraSpecs

import scala.concurrent.duration._

class UserDAOTest extends CassandraSpecs with EmbeddedDatabase with Cassandra.testConnector.Connector {

  implicit private val executionContext = ActorSystem().dispatcher

  override def beforeAll(): Unit = {
    database.create(5.seconds)
    super.beforeAll()
  }

  override def afterAll(): Unit = {
    database.drop(5.seconds)
    super.afterAll()
  }

  "User model" should "Add user to cassandra database" in {

    val user = UserDTO(UUID.fromString("f095fb92-0e4e-11e7-93ae-92361f002671"), "Julian", "Rodriguez", "julrod092")

    val future = this.database.userDAO.storeUser(user)

    whenReady(future) { result =>
      result shouldBe user
      drop(user.userName)
    }
  }

  "User model" should "get none from cassandra database if it not exist" in {

    val userName = "test"

    val future = database.userDAO.getUserByUserName(userName)

    whenReady(future) { result =>
      result shouldBe None
      drop(userName)
    }
  }

  "User model" should "get some user from cassandra database if it exist" in {

    val user = UserDTO(UUID.fromString("f095fb92-0e4e-11e7-93ae-92361f002671"), "Julian", "Rodriguez", "julrod092")

    database.userDAO.storeUser(user)
    val future = database.userDAO.getUserByUserName(user.userName)

    whenReady(future) { result =>
      result shouldBe Some(user)
      drop(user.userName)
    }
  }

  "User model" should "update the user info store in cassandra database" in {

    val user = UserDTO(UUID.fromString("f095fb92-0e4e-11e7-93ae-92361f002671"), "Julian", "Rodriguez", "julrod092")
    val update = user.copy(name = "Pedro")

    database.userDAO.storeUser(user)
    database.userDAO.storeUser(update)
    val future = database.userDAO.getUserByUserName(user.userName)

    whenReady(future) { result =>
      result shouldBe Some(update)
      drop(user.userName)
    }
  }

  "User model" should "delete a user info store in cassandra database" in {

    val user = UserDTO(UUID.fromString("f095fb92-0e4e-11e7-93ae-92361f002671"), "Julian", "Rodriguez", "julrod0921")

    database.userDAO.storeUser(user)
    database.userDAO.deleteUser(user.userName)
    val future = database.userDAO.getUserByUserName(user.userName)

    whenReady(future) { result =>
      result shouldBe None
    }
  }

  private def drop(user: String) = {
    for {
      username <- database.userDAO.deleteUser(user)
    } yield username
  }
}
