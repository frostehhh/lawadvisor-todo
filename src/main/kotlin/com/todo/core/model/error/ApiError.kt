package com.todo.core.model.error

import org.springframework.http.HttpStatus
import java.text.MessageFormat

interface ApiError {
    val statusCode: HttpStatus
    val code: String
    val messageTemplate: String

    fun toException(vararg errorParams: String, cause: Throwable? = null, data: Any? = null): Throwable {
        val message = getMessage(*errorParams)
        return ApiException(this, message, cause, data)
    }

    private fun getMessage(vararg params: String): String {
        return MessageFormat.format(messageTemplate, *params)
    }
}