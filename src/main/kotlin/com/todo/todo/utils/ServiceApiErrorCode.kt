package com.todo.todo.utils

import com.todo.core.model.error.ApiError
import org.springframework.http.HttpStatus

enum class ServiceApiErrorCode(
    override val statusCode: HttpStatus,
    override val messageTemplate: String,
): ApiError {

    override val code = name
}