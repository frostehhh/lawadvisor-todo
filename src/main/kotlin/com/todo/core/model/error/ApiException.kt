package com.todo.core.model.error

data class ApiException(
    val errorCode: ApiError,
    override val message: String,
    override val cause: Throwable? = null,
    val data: Any? = null,
) : RuntimeException(message, cause)
