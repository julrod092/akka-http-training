package com.example.infrastructure.broker

import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import com.example.infrastructure.WebServer.system
import com.example.infrastructure.dto.UserDTO
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.ExecutionContextExecutor

class KafkaProducer (implicit private val ec: ExecutionContextExecutor){

  private val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer).withBootstrapServers("localhost:9092")
  implicit private val materializer = ActorMaterializer()

  def sendMessage (userDTO: UserDTO) : Unit = {
    Source.single(userDTO)
      .map { elem =>
        new ProducerRecord[Array[Byte], String]("test", s"User created: ${elem.userName}")
      }
      .runWith(Producer.plainSink(producerSettings))
  }
}
