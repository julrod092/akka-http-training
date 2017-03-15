package com.example.users.persistence

import com.example.infrastructure.dao.UserDAO
import com.example.infrastructure.dto.UserDTO
import com.outworkers.phantom.dsl._

class UserModel extends CassandraTable[UserDAO, UserDTO] {

  override def tableName: String = "user_table"

  object id extends UUIDColumn(this)

  object userName extends StringColumn(this) with PartitionKey

  object name extends StringColumn(this)

  object lastName extends StringColumn(this)

  override def fromRow(r: Row): UserDTO = UserDTO(id(r), userName(r), name(r), lastName(r))
}

