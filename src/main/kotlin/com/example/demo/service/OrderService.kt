package com.example.demo.service

import com.example.demo.entity.OrderEntity
import com.example.demo.mapper.OrderMapper
import com.example.demo.messaging.producer.OrderMessageProducer
import com.example.demo.model.Order
import com.example.demo.model.OrderInput
import com.example.demo.repository.OrderRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class OrderService(val orderRepository: OrderRepository,
                   val orderMapper: OrderMapper,
                    val orderMessageProducer: OrderMessageProducer,
                    val objectMapper: ObjectMapper,
    val redisTemplate: RedisTemplate<String, String>

) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderService::class.java)
    }

    fun getAllOrders(): List<OrderEntity> = orderRepository.findAll()

    @Cacheable("orders", key="#id")
    fun getOrderById(id: Int) : Order {
        val optional = orderRepository.findById(id).orElseThrow()
        return orderMapper.toOrder(optional)
    }

    @CachePut("orders", key = "#result.id")
    @Transactional("transactionManager")
    fun saveOrder(orderInput: OrderInput): Order {
        var orderEntity = orderMapper.toOrderEntity(orderInput)
        logger.info("order input: $orderInput, entity: $orderEntity")
        //
        orderEntity.endDate = LocalDate.now().plusDays(7)
        orderEntity.endTime = LocalTime.now()
        orderEntity =  orderRepository.save(orderEntity)
        //
        orderMessageProducer.sendOnNewOrder(orderEntity)
        //
        return orderMapper.toOrder(orderEntity)
    }

    @CacheEvict("orders", key = "#id")
    fun deleteOrder(id : Int){
        orderRepository.deleteById(id)
    }

    @CacheEvict("orders", key = "#id")
    fun updateOrder(orderInput: OrderInput, id : Int): Order {
        val optional = orderRepository.findById(id).orElseThrow()
        var orderEntity = orderMapper.toOrderEntity(orderInput)
        orderEntity.id = id
        orderEntity = orderRepository.save(orderEntity)
        return orderMapper.toOrder(orderEntity)
    }

}