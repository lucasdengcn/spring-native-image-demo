package com.example.demo.exception

import org.springframework.http.HttpStatus

class RemoteResourceNotFoundException(val requestUrl: String,
                                      val status: HttpStatus,
                                      val responseBody: String?
    ) : RuntimeException("Error on Call: $requestUrl, $status, $responseBody") {
}