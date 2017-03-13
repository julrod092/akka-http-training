package com.example.database

import com.example.connector.Cassandra._
import com.example.model.ConcreteUserModel
import com.outworkers.phantom.dsl.{Database, KeySpaceDef}

/**
  * Created by seven4n on 13/03/17.
  */
class UserDatabase(override val connector: KeySpaceDef) extends Database[UserDatabase](connector) {

  object userModel extends ConcreteUserModel with connector.Connector
}

object ProductionDB extends UserDatabase(connector)

trait ProductionDatabaseProvider {
  def database: UserDatabase
}

trait ProductionDatabase extends ProductionDatabaseProvider {
  override val database = ProductionDB
}