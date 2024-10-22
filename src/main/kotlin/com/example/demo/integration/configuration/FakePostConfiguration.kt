package com.example.demo.integration.configuration

import com.example.demo.integration.ApiErrorDecoder
import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FakePostConfiguration {

    @Bean
    fun errorDecoder() : ErrorDecoder {
        return ApiErrorDecoder()
    }

}