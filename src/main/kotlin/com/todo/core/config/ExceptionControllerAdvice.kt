package com.todo.core.config

import com.todo.core.model.error.ApiErrorResponse
import com.todo.core.model.error.ApiException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionControllerAdvice {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ExceptionControllerAdvice::class.java)
    }

    @ExceptionHandler(value = [ApiException::class])
    fun handleApiException(e: ApiException): ResponseEntity<ApiErrorResponse> {
        LOGGER.error(
                "Encountered error while processing request: message={}, data={}",
                e.message,
                e.data,
                e
        )
        return ResponseEntity
            .status(e.errorCode.statusCode)
            .body(ApiErrorResponse(message = e.message, errorCode = e.errorCode.code, data = e.data))
    }
}