package com.example.users.persistence

import com.example.infrastructure.database.ProductionDatabase
import com.example.infrastructure.dto.UserDTO
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

trait UserRepository extends ProductionDatabase {

  def fetch(userName: String): Future[Option[UserDTO]] = {
    database.userDAO.getUserByUserName(userName)
  }

  def add(user: UserDTO): Future[UserDTO] = {
    for {
      store <- database.userDAO.storeUser(user)
    } yield store
  }

  def update(user: UserDTO): Future[UserDTO] = {
    for {
      store <- database.userDAO.storeUser(user)
    } yield store
  }

  def delete(userName: String) : Future[String] = {
    for {
      delete <- database.userDAO.deleteUser(userName)
    } yield delete
  }
}

object UserRepository extends UserRepository
