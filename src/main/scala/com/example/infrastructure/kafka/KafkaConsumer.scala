package com.example.infrastructure.kafka

import akka.Done
import akka.actor.ActorSystem
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.{ByteArrayDeserializer, StringDeserializer}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by seven4n on 23/03/17.
  */
object KafkaConsumer {

  def reciever(implicit system: ActorSystem, materializer: ActorMaterializer, ec: ExecutionContextExecutor): Future[Done] = {
    val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new StringDeserializer)
      .withBootstrapServers("localhost:9092")
      .withGroupId("group1")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

    Consumer.committableSource(consumerSettings, Subscriptions.topics("users"))
      .runForeach(msg =>
        println(msg.record.value))
  }
}
