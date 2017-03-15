package com.example.users.persistence

import com.example.infrastructure.dao.UserDAO
import com.example.users.persistence.Cassandra._
import com.outworkers.phantom.dsl._


trait ProductionDatabaseProvider {
  def database: UserDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDB
}

object ProductionDB extends UserDatabase(cassandraConnector)

class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userDAO extends UserDAO with connector.Connector

}