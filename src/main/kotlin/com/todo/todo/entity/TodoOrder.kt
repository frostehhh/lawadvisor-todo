package com.todo.todo.entity

import jakarta.persistence.*
import java.util.*

@Entity
@Table(schema = "todo", name = "todo_order")
data class TodoOrder(
    @Id
    val id: UUID,
    val userId: UUID,
    val prevTodo: UUID? = null,
    val nextTodo: UUID? = null,
)