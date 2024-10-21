package com.example.demo

import com.example.demo.model.OrderInput
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertContains

@SpringBootTest
class ObjectMapperTests (@Autowired val objectMapper: ObjectMapper) {

    @Test
    fun `test serialize object will include class info`(){
        val orderInput = OrderInput(1,2,3,4);
        val asString = objectMapper.writeValueAsString(orderInput)
        println(asString)
        assertContains(asString, "\"customerId\":1")
    }

}