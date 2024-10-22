package com.example.demo.employee.apis.handlers

import com.example.demo.exception.APIIntegrationException
import com.example.demo.exception.RemoteResourceNotFoundException
import feign.FeignException
import io.github.resilience4j.bulkhead.BulkheadFullException
import io.github.resilience4j.circuitbreaker.CallNotPermittedException
import jakarta.persistence.EntityNotFoundException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import java.net.URI
import java.net.URISyntaxException
import java.util.function.Consumer

@RestControllerAdvice
class GlobalExceptionHandler {

    companion object {
        val log: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    }

    private fun buildProblemDetail(
        title: String,
        ex: Exception,
        status: HttpStatus,
        request: WebRequest,
    ): ProblemDetail {
        val problemDetail = ProblemDetail.forStatus(status)
        problemDetail.detail = ex.message
        problemDetail.setProperty("traceId", getTraceId(request))
        problemDetail.title = title
        try {
            problemDetail.instance = URI(getRequestUri(request))
        } catch (e: URISyntaxException) {
            log.error(e.message, e)
        }
        return problemDetail
    }

    private fun getRequestUri(request: WebRequest): String {
        return if (request is ServletWebRequest) {
            request.request.requestURI
        } else {
            "Unknown"
        }
    }

    private fun getTraceId(request: WebRequest): String? {
        return if (request is ServletWebRequest) {
            request.getHeader("trace-id")
        } else {
            "Unknown"
        }
    }

    @ExceptionHandler(IllegalStateException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleIllegalStateException(
        ex: IllegalStateException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("IllegalState: ", ex)
        val errorResponse = buildProblemDetail("IllegalState", ex, HttpStatus.BAD_REQUEST, request)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleEntityNotFoundException(
        ex: EntityNotFoundException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Entity NotFound: {}", request, ex)
        val errorResponse = buildProblemDetail("Not Found", ex, HttpStatus.NOT_FOUND, request)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(RemoteResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected fun handleRemoteResourceNotFoundException(
        ex: RemoteResourceNotFoundException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Remote Resource NotFound: {}", request, ex)
        val errorResponse = buildProblemDetail("Not Found", ex, HttpStatus.NOT_FOUND, request)
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected fun handleHttpMediaTypeNotAcceptableException(
        ex: HttpMediaTypeNotAcceptableException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Request Not Supported: ", ex)
        val errorResponse = buildProblemDetail("Request Not Supported", ex, HttpStatus.NOT_ACCEPTABLE, request)
        return ResponseEntity(errorResponse, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    protected fun httpMediaTypeNotSupportedException(
        ex: HttpMediaTypeNotSupportedException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Request Not Supported: ", ex)
        val errorResponse = buildProblemDetail("Request Not Supported", ex, HttpStatus.NOT_ACCEPTABLE, request)
        return ResponseEntity(errorResponse, HttpStatus.NOT_ACCEPTABLE)
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    protected fun handleHttpRequestMethodNotSupportedException(
        ex: HttpRequestMethodNotSupportedException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Request Not Supported: ", ex)
        val errorResponse = buildProblemDetail("Request Not Supported", ex, HttpStatus.METHOD_NOT_ALLOWED, request)
        return ResponseEntity(errorResponse, HttpStatus.METHOD_NOT_ALLOWED)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected fun handleRuntimeException(
        ex: Exception, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("UnexpectedError: ", ex)
        val errorResponse = buildProblemDetail("Request Error", ex, HttpStatus.INTERNAL_SERVER_ERROR, request)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected fun handleMethodArgumentNotValidException(
        ex: MethodArgumentNotValidException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("Request Input Invalid: ", ex)
        val errorMessages = ArrayList<String?>()
        ex.fieldErrors.forEach(Consumer { fieldError: FieldError -> errorMessages.add(fieldError.defaultMessage) })
        val join = java.lang.String.join(",", errorMessages)
        val errorResponse = buildProblemDetail("Request Input Invalid", ex, HttpStatus.BAD_REQUEST, request)
        errorResponse.detail = join
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(APIIntegrationException::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    protected fun handleAPIIntegrationException(
        ex: APIIntegrationException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("API Integration Error: ", ex)
        val errorResponse = buildProblemDetail("API Integration Error", ex, ex.status, request)
        errorResponse.setProperty("remoteUrl", ex.requestUrl)
        return ResponseEntity(errorResponse, HttpStatus.BAD_GATEWAY)
    }

    @ExceptionHandler(FeignException::class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    protected fun handleFeignException(
        ex: FeignException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("API Integration Error: ", ex)
        val errorResponse = buildProblemDetail("API Integration Error", ex, HttpStatus.valueOf(ex.status()), request)
        errorResponse.setProperty("remoteUrl", ex.request().url())
        return ResponseEntity(errorResponse, HttpStatus.BAD_GATEWAY)
    }

    @ExceptionHandler(CallNotPermittedException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected fun handleCallNotPermittedException(
        ex: CallNotPermittedException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("API Call Not Permitted on Breaker: ", ex)
        val errorResponse = buildProblemDetail("API Call Not Permitted on Breaker", ex, HttpStatus.SERVICE_UNAVAILABLE, request)
        errorResponse.setProperty("breaker", ex.causingCircuitBreakerName)
        return ResponseEntity(errorResponse, HttpStatus.SERVICE_UNAVAILABLE)
    }

    @ExceptionHandler(BulkheadFullException::class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    protected fun handleBulkheadFullException(
        ex: BulkheadFullException, request: WebRequest,
    ): ResponseEntity<ProblemDetail> {
        log.error("API Call Not Permitted on Breaker: ", ex)
        val errorResponse = buildProblemDetail("API Call Not Permitted on Breaker", ex, HttpStatus.SERVICE_UNAVAILABLE, request)
        return ResponseEntity(errorResponse, HttpStatus.SERVICE_UNAVAILABLE)
    }
}
