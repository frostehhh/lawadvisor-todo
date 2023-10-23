package com.todo.todo.dto

import com.todo.todo.entity.Todo
import java.time.OffsetDateTime
import java.util.UUID

data class UpdateTodoDto(
    val title: String?,
    val details: String?,
)

fun UpdateTodoDto.toEntity(id: UUID, userId: UUID, createdOn: OffsetDateTime, updatedOn: OffsetDateTime) = Todo(
    id = id,
    userId = userId,
    title = title,
    details = details,
    createdOn = createdOn,
    updatedOn = updatedOn,
)