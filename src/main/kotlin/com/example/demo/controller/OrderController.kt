package com.example.demo.controller

import com.example.demo.entity.OrderEntity
import com.example.demo.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders/v1")
class OrderController (val orderService: OrderService) {


    @PostMapping("/")
    fun create(
        @RequestBody orderRequest: OrderEntity,
    ): ResponseEntity<OrderEntity> {
        val order = orderService.saveOrder(orderRequest)
        return ResponseEntity.ok(order)
    }

    @GetMapping("/{id}")
    fun getOrder(
        @PathVariable id : Int,
    ): ResponseEntity<OrderEntity> {
        val order = orderService.getOrderById(id);
        return ResponseEntity.ok(order)
    }

    @DeleteMapping("/{id}")
    fun deleteOrder(
        @PathVariable id : Int,
    ): ResponseEntity<Boolean> {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(true)
    }

    @PutMapping("/{id}")
    fun updateOrder(
        @PathVariable id : Int,
        @RequestBody orderRequest: OrderEntity,
    ): ResponseEntity<OrderEntity> {
        val updateOrder = orderService.updateOrder(orderRequest, id)
        return ResponseEntity.ok(updateOrder)
    }

    @GetMapping("/{page}/{size}")
    fun getOrders(
        @PathVariable page : Int,
        @PathVariable size : Int
    ): ResponseEntity<List<OrderEntity>> {
        val orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders)
    }
}