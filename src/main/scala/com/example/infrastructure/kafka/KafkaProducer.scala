package com.example.infrastructure.kafka

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.ExecutionContextExecutor

class KafkaProducer(implicit ec: ExecutionContextExecutor, system: ActorSystem, materializer: ActorMaterializer) {

  private val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("localhost:9092")

  def sendMessage(userName: String): String = {
    val message = s"User created: $userName"
    Source.single(message)
      .map { msg =>
        new ProducerRecord[Array[Byte], String]("test", msg)
      }
      .runWith(Producer.plainSink(producerSettings))
    message
  }
}
