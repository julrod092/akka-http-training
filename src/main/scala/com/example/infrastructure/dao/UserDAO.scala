package com.example.infrastructure.dao

import com.example.infrastructure.dto.UserDTO
import com.example.users.persistence.UserModel
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

abstract class UserDAO extends UserModel with RootConnector {

  def getUserByUserName(userName: String): Future[Option[UserDTO]] = {
    select
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def storeUser(user: UserDTO): Future[ResultSet] = {
    insert
      .value(_.id, user.id)
      .value(_.userName, user.userName)
      .value(_.name, user.name)
      .value(_.lastName, user.lastName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def deleteUser(userName: String): Future[ResultSet] = {
    delete
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
}
