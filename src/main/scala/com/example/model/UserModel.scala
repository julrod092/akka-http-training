package com.example.model

import com.example.entity.User
import com.outworkers.phantom.column.TimeUUIDColumn
import com.outworkers.phantom.dsl.{CassandraTable, ClusteringOrder, ConsistencyLevel, PartitionKey, ResultSet, RootConnector, Row, StringColumn}

import scala.concurrent.Future

class UserModel extends CassandraTable[ConcreteUserModel, User] {

  override def tableName: String = "user_table"

  object id extends TimeUUIDColumn(this) with ClusteringOrder {
    override lazy val name = "user_id"
  }

  object userName extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object lastName extends StringColumn(this)

  override def fromRow(r: Row): User = User(id(r), userName(r), name(r), lastName(r))
}

abstract class ConcreteUserModel extends UserModel with RootConnector {

  def getUserByUsername(userName: String): Future[List[User]] = {
    select
      .where(_.userName eqs userName)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def storeUser(user: User): Future[ResultSet] = {
    insert
      .value(_.id, user.id)
      .value(_.userName, user.userName)
      .value(_.name, user.name)
      .value(_.lastName, user.)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .future()
  }
}