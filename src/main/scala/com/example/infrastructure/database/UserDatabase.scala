package com.example.infrastructure.database

import com.example.infrastructure.dao.UserDAO
import com.example.infrastructure.database.Cassandra._
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