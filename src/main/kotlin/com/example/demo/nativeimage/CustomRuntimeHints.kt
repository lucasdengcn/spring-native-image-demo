package com.example.demo.nativeimage

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.aot.hint.TypeReference
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
        val clazz = TypeReference.of("com.github.benmanes.caffeine.cache.SSLMSA")
        hints.reflection().registerType(clazz) {
            it.withMembers(MemberCategory.INVOKE_PUBLIC_CONSTRUCTORS)
            it.withMembers(MemberCategory.INVOKE_PUBLIC_METHODS)
            it.withMembers(MemberCategory.DECLARED_FIELDS)
            it.withMembers(MemberCategory.PUBLIC_CLASSES)
        }
    }
}