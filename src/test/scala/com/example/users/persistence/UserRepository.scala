package com.example.users.persistence

import java.util.UUID

import com.example.infrastructure.dto.UserDTO
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

trait UserRepository {

  def fetch(userName: String): Future[Option[UserDTO]] = userName match {
    case "julrod0921" => Future(None)
    case "julrod092" => Future(Option(UserDTO(UUID.fromString("5d50893c-0995-11e7-93ae-92361f002671"), "Julian", "Rodriguez", "julrod092")))
  }

  def add(user: UserDTO): Future[UserDTO] = {
    Future(user)
  }

  def update(user: UserDTO): Future[UserDTO] = {
    add(user)
  }

  def delete(userName: String) : Future[String] = {
    Future(userName)
  }
}

object UserRepository extends UserRepository
