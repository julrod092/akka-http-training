package com.example.infrastructure.database

import com.example.infrastructure.dao.UserDAO
import com.example.infrastructure.database.Cassandra._
import com.outworkers.phantom.dsl._


class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userDAO extends UserDAO with connector.Connector

}

// Production

trait ProductionDatabaseProvider {
  def database: UserDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDB
}

object ProductionDB extends UserDatabase(cassandraConnector)


// Test

object EmbeddedDb extends UserDatabase(testConnector)

trait EmbeddedDatabaseProvider {
  def database: UserDatabase
}

trait EmbeddedDatabase extends EmbeddedDatabaseProvider {
  override val database = EmbeddedDb
}