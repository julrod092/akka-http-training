package com.example.users.persistence

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoints}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConverters._

/**
  * Created by seven4n on 13/03/17.
  */
object Cassandra {

  private val cassandraConf = ConfigFactory.load()

  private val hosts = cassandraConf.getStringList("cassandra.host")
  private val keyspace = cassandraConf.getString("cassandra.keyspace")
  private val username = cassandraConf.getString("cassandra.username")
  private val password = cassandraConf.getString("cassandra.password")

  lazy val cassandraConnector: CassandraConnection = ContactPoints(hosts.asScala)
    .withClusterBuilder(_.withCredentials(username, password))
    .keySpace(keyspace)
}
