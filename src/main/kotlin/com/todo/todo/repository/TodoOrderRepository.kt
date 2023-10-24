package com.todo.todo.repository

import com.todo.todo.entity.TodoOrder
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TodoOrderRepository: JpaRepository<TodoOrder, UUID> {
    fun findByUserIdAndNextTodoIsNull(userId: UUID): TodoOrder?
    fun findByUserIdAndPrevTodoIsNull(userId: UUID): TodoOrder?
}

