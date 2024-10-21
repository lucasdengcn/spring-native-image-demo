package com.example.demo.model

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull

@Schema
data class OrderInput(

    @Parameter(description = "Customer's identifier", required = true, example = "123")
    @field:NotNull(message = "customer id should not be NULL")
    val customerId: Int?,

    @Parameter(description = "Product's identifier", required = true, example = "456")
    @field:NotNull(message = "product id should not be NULL")
    val productId: Int?,

    @Parameter(description = "the number of product purchased", required = true, example = "2")
    @field:NotNull(message = "product count should not be NULL")
    val productCount: Int?,

    @Parameter(description = "Product's price", required = true, example = "123")
    @field:NotNull(message = "price should not be NULL")
    val price: Int?,
){
    constructor() : this(null, null, null, null)
}