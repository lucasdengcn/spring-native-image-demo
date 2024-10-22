package com.example.demo.integration

import com.example.demo.exception.APIIntegrationException
import com.example.demo.exception.RemoteResourceNotFoundException
import feign.Response
import feign.codec.ErrorDecoder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.util.StreamUtils
import java.io.IOException
import java.nio.charset.StandardCharsets


class ApiErrorDecoder : ErrorDecoder {

    companion object {
        val logger: Logger = LoggerFactory.getLogger(ApiErrorDecoder::class.java)
    }

    override fun decode(methodKey: String?, response: Response?): Exception {
        val requestUrl = response!!.request().url()
        val responseStatus = HttpStatus.valueOf(response!!.status())

        var responseBody: String? = null
        try {
            val body = response!!.body()
            if (body.length() > 0) {
                responseBody = StreamUtils.copyToString(body.asInputStream(), StandardCharsets.UTF_8)
            }
        } catch (e: IOException) {
            // Handle or log the exception
            responseBody = "IOException"
        }

        return if (responseStatus.is5xxServerError) {
            APIIntegrationException(requestUrl, responseStatus, responseBody)
        } else if (responseStatus.isSameCodeAs(HttpStatus.NOT_FOUND)) {
            RemoteResourceNotFoundException(requestUrl, responseStatus, responseBody)
        } else if (responseStatus.is4xxClientError) {
            APIIntegrationException(requestUrl, responseStatus, responseBody)
        } else {
            Exception("Generic exception")
        }
    }
}