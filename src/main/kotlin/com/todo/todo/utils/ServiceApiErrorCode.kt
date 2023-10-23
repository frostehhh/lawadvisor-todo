package com.todo.todo.utils

import com.todo.core.model.error.ApiError
import org.springframework.http.HttpStatus

enum class ServiceApiErrorCode(
    override val statusCode: HttpStatus,
    override val messageTemplate: String,
): ApiError {
    TODO_NOT_FOUND(
        HttpStatus.NOT_FOUND,
        "Todo record not found"
    ),
    ;

    override val code = name
}