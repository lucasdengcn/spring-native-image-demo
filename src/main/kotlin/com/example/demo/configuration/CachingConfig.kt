package com.example.demo.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.cache.CacheProperties
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


private const val TIME_FORMAT = "HH:mm:ss"

private const val DATE_FORMAT = "yyyy-MM-dd"

private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

@Configuration
@EnableCaching
class CachingConfig : CachingConfigurer {


    override fun errorHandler(): CacheErrorHandler? {
        return CacheErrorHandlerImpl()
    }

    @Primary
    @Bean
    fun objectMapper(): ObjectMapper {
        var objectMapper = ObjectMapper()
        //
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) // Exclude null values from serialization
        objectMapper.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
        ) // Ignore unknown properties during deserialization
        // Add more custom configurations as needed
        registerJavaTimeModule(objectMapper)
        return objectMapper
    }

    @Bean
    fun redisSerializer() : GenericJackson2JsonRedisSerializer {
        //
        var objectMapper = ObjectMapper()
        // Configure ObjectMapper properties here
        objectMapper.activateDefaultTyping(objectMapper.polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.EVERYTHING, JsonTypeInfo.As.WRAPPER_OBJECT);
        //
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL) // Exclude null values from serialization
        objectMapper.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
            false
        ) // Ignore unknown properties during deserialization
        // Add more custom configurations as needed
        registerJavaTimeModule(objectMapper)
        //
        return GenericJackson2JsonRedisSerializer(objectMapper)
    }

    @Bean
    fun redisCacheConfiguration(cacheProperties: CacheProperties, serializer: GenericJackson2JsonRedisSerializer): RedisCacheConfiguration {
        var redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()

        // 先载入配置文件中的配置信息
        val redisProperties = cacheProperties.redis

        // 根据配置文件中的定义，初始化 Redis Cache 配
        if (redisProperties.timeToLive != null) {
            redisCacheConfiguration = redisCacheConfiguration.entryTtl(redisProperties.timeToLive)
        }
        if (StringUtils.hasText(redisProperties.keyPrefix)) {
            redisCacheConfiguration = redisCacheConfiguration.prefixCacheNameWith(redisProperties.keyPrefix)
        }
        if (!redisProperties.isCacheNullValues) {
            redisCacheConfiguration = redisCacheConfiguration.disableCachingNullValues()
        }
        if (!redisProperties.isUseKeyPrefix) {
            redisCacheConfiguration = redisCacheConfiguration.disableKeyPrefix()
        }

        // 设置 Value 的序列化方式
        return redisCacheConfiguration
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))
    }

    private fun registerJavaTimeModule(objectMapper: ObjectMapper) {
        // 缓存对象中可能会有 LocalTime/LocalDate/LocalDateTime 等 java.time 段，所以需要通过 JavaTimeModule 定义其序列化、反序列化格式
        val javaTimeModule = JavaTimeModule()
        javaTimeModule.addDeserializer(
            LocalTime::class.java,
            LocalTimeDeserializer(DateTimeFormatter.ofPattern(TIME_FORMAT))
        )
        javaTimeModule.addDeserializer(
            LocalDate::class.java,
            LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT))
        )
        javaTimeModule.addDeserializer(
            LocalDateTime::class.java,
            LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
        )
        javaTimeModule.addSerializer(
            LocalTime::class.java,
            LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_FORMAT))
        )
        javaTimeModule.addSerializer(
            LocalDate::class.java,
            LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT))
        )
        javaTimeModule.addSerializer(
            LocalDateTime::class.java,
            LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT))
        )
        //
        objectMapper.registerModules(javaTimeModule)
    }

}