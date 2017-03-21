package com.example.users.util

import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

abstract class CassandraSpecs extends FlatSpec
  with BeforeAndAfterAll
  with Matchers
  with Inspectors
  with ScalaFutures
  with OptionValues