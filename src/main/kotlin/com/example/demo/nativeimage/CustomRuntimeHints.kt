package com.example.demo.nativeimage

import com.example.demo.service.OrderService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportRuntimeHints

@Configuration
@ImportRuntimeHints(CustomRuntimeHints::class)
class CustomRuntimeHints : RuntimeHintsRegistrar {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(CustomRuntimeHints::class.java)
    }

    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        logger.info("registerHints")
    }
}