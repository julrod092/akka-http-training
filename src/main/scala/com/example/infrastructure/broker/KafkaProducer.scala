package com.example.infrastructure.broker

import akka.http.scaladsl.model.StatusCodes
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.Source
import com.example.infrastructure.WebServer.system
import com.example.infrastructure.dto.UserDTO
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.Future

object KafkaProducer {

  private val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer).withBootstrapServers("localhost:9092")

  def sendMessage (userDTO: UserDTO): Future[(StatusCodes, String)] = {
    Source.single(userDTO)
      .map { elem =>
        new ProducerRecord[Array[Byte], String]("test", s"User created: ${elem.userName}")
      }
      .runWith(Producer.plainSink(producerSettings)).map(_ => (StatusCodes.OK, s"User created: ${userDTO.userName}"))
  }
}
