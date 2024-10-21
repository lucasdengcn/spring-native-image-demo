package com.example.demo

import com.example.demo.model.OrderInput
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer

@SpringBootTest
class RedisSerializerTests (@Autowired val serializer: GenericJackson2JsonRedisSerializer) {

    @Test
    fun `test serialize object will include class info`(){
        val orderInput = OrderInput(1,2,3,4);
        val bytes = serializer.serialize(orderInput)
        //
        val asString = String(bytes)
        println(asString)
    }

    @Test
    fun `test serialize list of object will include class info`(){
        val orderInput = OrderInput(1,2,3,4);
        val bytes = serializer.serialize(listOf(orderInput, orderInput, orderInput, orderInput))
        val asString = String(bytes)
        println(asString)
    }

}