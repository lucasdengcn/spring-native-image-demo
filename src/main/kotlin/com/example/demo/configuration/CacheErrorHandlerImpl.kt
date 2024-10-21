package com.example.demo.configuration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.interceptor.CacheErrorHandler
import java.lang.RuntimeException

class CacheErrorHandlerImpl : CacheErrorHandler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(CacheErrorHandlerImpl::class.java)
    }

    override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
        log.error("CacheGetError, cache_name={}, key={}", cache.getName(), key, exception);
    }

    override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
        log.error("CachePutError, cache_name={}, key={}", cache.getName(), key, exception);
    }

    override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
        log.error("CacheEvictError, cache_name={}, key={}", cache.getName(), key, exception);
    }

    override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
        log.error("CacheClearError cache_name={}, key={}", cache.getName(), exception);
    }
}