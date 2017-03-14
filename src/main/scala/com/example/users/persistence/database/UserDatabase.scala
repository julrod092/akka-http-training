package com.example.users.persistence.database

import com.example.users.persistence.connector.Cassandra._
import com.example.users.persistence.model.ConcreteUserModel
import com.outworkers.phantom.dsl._


trait ProductionDatabaseProvider {
  def database: UserDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDB
}

object ProductionDB extends UserDatabase(connector)

class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userModel extends ConcreteUserModel with connector.Connector

}