package com.example.demo.model

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * do not set default value, otherwise MapStruct will not assign value to properties.
 */

@Schema
data class Order (

    var id: Int?,

    var customerId: Int?,

    var productId: Int?,

    var productCount: Int?,

    var price: Int?,

    var createdDate: LocalDateTime?,

    var updatedDate: LocalDateTime?,

    var endDate: LocalDate?,

    var endTime: LocalTime?
){
    constructor() : this(null, null,
        null, null, null,
        null, null,
        null, null)
}