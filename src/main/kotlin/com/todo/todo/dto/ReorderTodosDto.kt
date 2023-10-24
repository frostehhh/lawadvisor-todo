package com.todo.todo.dto

import java.util.*

data class ReorderTodosDto(
    val userId: UUID,
    val positions: List<ReorderTodoEntry>
)

data class ReorderTodoEntry(
    val id: UUID,
    val prevTodo: UUID?,
)
