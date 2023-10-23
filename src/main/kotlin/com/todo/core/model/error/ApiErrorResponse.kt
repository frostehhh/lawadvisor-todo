package com.todo.core.model.error

data class ApiErrorResponse(
    val message: String,
    val errorCode: String? = null,
    val data: Any? = null,
)
