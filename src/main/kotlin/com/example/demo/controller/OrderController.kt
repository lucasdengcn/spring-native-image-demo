package com.example.demo.controller

import com.example.demo.entity.OrderEntity
import com.example.demo.model.Order
import com.example.demo.model.OrderInput
import com.example.demo.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Order APIs")
@RestController
@RequestMapping("/orders")
@Validated
class OrderController (val orderService: OrderService) {


    @Operation(description = "create a Order")
    @PostMapping("/v1/")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @Valid @RequestBody orderRequest: OrderInput,
    ): ResponseEntity<Order> {
        val order = orderService.saveOrder(orderRequest)
        return ResponseEntity.ok(order)
    }

    @Operation(description = "get a Order detail")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/{id}")
    fun getOrder(
        @PathVariable id : Int,
    ): ResponseEntity<Order> {
        val order = orderService.getOrderById(id);
        return ResponseEntity.ok(order)
    }

    @Operation(description = "delete a Order via Order's id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/v1/{id}")
    fun deleteOrder(
        @PathVariable id : Int,
    ): ResponseEntity<Boolean> {
        orderService.deleteOrder(id);
        return ResponseEntity.ok(true)
    }

    @Operation(description = "update a Order")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/v1/{id}")
    fun updateOrder(
        @PathVariable id : Int,
        @Valid @RequestBody orderRequest: OrderInput,
    ): ResponseEntity<Order> {
        val updateOrder = orderService.updateOrder(orderRequest, id)
        return ResponseEntity.ok(updateOrder)
    }

    @Operation(description = "get pageable orders ")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/v1/{page}/{size}")
    fun getOrders(
        @PathVariable page : Int,
        @PathVariable size : Int
    ): ResponseEntity<List<OrderEntity>> {
        val orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders)
    }

}