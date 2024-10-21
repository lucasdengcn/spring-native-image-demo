package com.example.demo.mapper

import com.example.demo.entity.OrderEntity
import com.example.demo.model.Order
import com.example.demo.model.OrderInput
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface OrderMapper {
    //
    fun toOrder(orderEntity: OrderEntity) : Order
    //
    fun toOrders(orderEntity: List<OrderEntity>) : List<Order>
    //
    fun toOrderEntity(orderInput: OrderInput) : OrderEntity
}