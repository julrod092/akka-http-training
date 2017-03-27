package com.example.infrastructure.kafka

import akka.actor.ActorSystem
import akka.kafka.ProducerSettings
import akka.kafka.scaladsl.Producer
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.{ByteArraySerializer, StringSerializer}

import scala.concurrent.{ExecutionContextExecutor, Future}

class KafkaProducer(implicit ec: ExecutionContextExecutor, system: ActorSystem, materializer: ActorMaterializer) {

  private val producerSettings = ProducerSettings(system, new ByteArraySerializer, new StringSerializer)
    .withBootstrapServers("localhost:9092")

  def sendMessage(userName: String, method:String): Future[String] = {
    val message = s"User $method: $userName"
    Source.single(message)
      .map { msg =>
        new ProducerRecord[Array[Byte], String]("users", msg)
      }
      .runWith(Producer.plainSink(producerSettings))
      .map(sucess => message)
  }
}
