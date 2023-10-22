package com.todo.todo.repository

import com.todo.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TodoRepository : JpaRepository<Todo, UUID> {
    fun findByUserId(userId: UUID): List<Todo>
}