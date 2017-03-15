package com.example.users.persistence.model

import com.example.users.persistence.entity.User
import com.outworkers.phantom.dsl._

import scala.concurrent.Future

class UserModel extends CassandraTable[ConcreteUserModel, User] {

  override def tableName: String = "user_table"

  object id extends IntColumn(this)

  object userName extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object lastName extends StringColumn(this)

  override def fromRow(r: Row): User = User(id(r), userName(r), name(r), lastName(r))
}

abstract class ConcreteUserModel extends UserModel with RootConnector {

  def getUserByUserName(userName: String): Future[Option[User]] = {
    select
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def storeUser(user: User): Future[ResultSet] = {
    insert
      .value(_.id, user.id)
      .value(_.userName, user.userName)
      .value(_.name, user.name)
      .value(_.lastName, user.lastName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }

  def deleteByUserName(userName: String): Future[ResultSet] = {
    delete
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
}