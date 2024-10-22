package com.example.demo.exception

import org.springframework.http.HttpStatus

class APIIntegrationException(val requestUrl: String,
                              val status: HttpStatus,
                              val responseBody: String?
    ) : RuntimeException("Error on Call: $requestUrl, $status, $responseBody") {
}