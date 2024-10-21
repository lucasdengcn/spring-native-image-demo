package com.example.demo.messaging.consumer

import com.example.demo.entity.OrderEntity
import com.example.demo.service.OrderService
import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class OrderMessageConsumer {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderMessageConsumer::class.java)
    }

//    @Bean
//    fun newOrderTopic() {
//        NewTopic("order_create", 1, 1)
//    }

    @KafkaListener(topics = ["order_create"])
    fun newOrder(value: OrderEntity?, acknowledgment: Acknowledgment){
        logger.info("newOrder receive: $value")
        // Manually acknowledge and commit the offset
        acknowledgment.acknowledge()
    }


}