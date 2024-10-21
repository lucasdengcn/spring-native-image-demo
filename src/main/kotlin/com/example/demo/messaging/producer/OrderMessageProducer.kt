package com.example.demo.messaging.producer

import com.example.demo.entity.OrderEntity
import com.example.demo.messaging.consumer.OrderMessageConsumer
import com.example.demo.model.Order
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class OrderMessageProducer (
    val kafkaTemplate: KafkaTemplate<String, Any>,
    val objectMapper: ObjectMapper
)
{
    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderMessageProducer::class.java)
    }

    @Transactional("transactionManager")
    fun sendOnNewOrder(order: OrderEntity) {
        val completableFuture = kafkaTemplate.send(
            "order_create",
            "order_${order.id}",
            order
        )
        // add callback to print out result
        completableFuture.whenCompleteAsync { result, exception ->
            if (exception != null) {
                logger.error("ERROR sending to Kafka", exception)
            } else {
                val metadata = result.recordMetadata
                logger.info("Message sent to topic ${metadata.topic()}, partition: ${metadata.partition()}, offset: ${metadata.offset()}")
            }
        }
    }

}