package com.example.users.persistence.service

import com.example.users.persistence.database.ProductionDatabase
import com.example.users.persistence.entity.User
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

trait UserService extends ProductionDatabase {

  def getUserByUserName (userName: String): Future[Option[User]] = {
    database.userModel.getUserByUserName(userName)
  }

  def storeUser (user: User): Future[ResultSet] = {
    for {
      store <- database.userModel.storeUser(user)
    } yield store
  }

  def delete(userName: String): Future[ResultSet] = {
    for {
      delete <- database.userModel.deleteByUserName(userName)
    } yield delete
  }
}

object UserService extends UserService with ProductionDatabase
