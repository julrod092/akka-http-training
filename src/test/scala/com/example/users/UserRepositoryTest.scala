package com.example.users

import java.util.concurrent.Executors

import com.datastax.driver.core.utils.UUIDs
import com.example.infrastructure.database.EmbeddedDatabase
import com.example.infrastructure.dto.UserDTO
import com.example.users.persistence.UserRepository
import com.outworkers.util.samplers._
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}
import scala.concurrent.duration._

class UserRepositoryTest extends WordSpec with Matchers with BeforeAndAfterAll
  with EmbeddedDatabase with Inspectors with ScalaFutures with OptionValues {

  implicit val ec: ExecutionContextExecutorService = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))

  override def beforeAll(): Unit = {
    database.create(5.seconds)(ec)
  }

  implicit object SongGenerator extends Sample[UserDTO] {
    override def sample: UserDTO = {
      UserDTO(
        id = UUIDs.timeBased(),
        userName = gen[ShortString].value,
        name = "Toxicity",
        lastName = "System"
      )
    }
  }

  "User service" should {

    "find a song by id" in {
      val sample = gen[UserDTO]

      UserRepository.add(sample)
      val res = UserRepository.fetch(sample.userName)

      whenReady(res) { res =>
        res shouldBe defined
      }
    }
  }
}
