package com.todo.todo.dto

import com.todo.todo.entity.Todo
import java.time.OffsetDateTime
import java.util.UUID

data class CreateTodoDto(
        val userId: UUID,
        val title: String?,
        val details: String?,
)

fun CreateTodoDto.toEntity(currentTime: OffsetDateTime) = Todo(
    userId = userId,
    title = title,
    details = details,
    createdOn = currentTime,
    updatedOn = currentTime,
)