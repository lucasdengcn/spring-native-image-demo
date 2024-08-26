package com.example.demo.service

import com.example.demo.entity.OrderEntity
import com.example.demo.repository.OrderRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(OrderService::class.java)
    }

    fun getAllOrders() = orderRepository.findAll()

    fun getOrderById(id: Int) : OrderEntity {
        val optional = orderRepository.findById(id)
        return optional.get()
    }

    fun saveOrder(order : OrderEntity): OrderEntity {
        return orderRepository.save(order)
    }

    fun deleteOrder(id : Int){
        orderRepository.deleteById(id)
    }

    fun updateOrder(order : OrderEntity, id : Int): OrderEntity {
        val optional = orderRepository.findById(id)
        if (optional.isPresent) {
            return orderRepository.save(order)
        } else {
            throw Exception("Order not found")
        }
    }

}