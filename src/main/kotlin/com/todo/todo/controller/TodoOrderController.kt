package com.todo.todo.controller

import com.todo.todo.dto.ReorderTodosDto
import com.todo.todo.service.TodoOrderService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/todos/orders")
class TodoOrderController(
    private val todoOrderService: TodoOrderService
) {
    @PutMapping
    fun reorderTodos(
        @RequestBody reorderTodosRequest: ReorderTodosDto
    ) {
        return todoOrderService.reorderTodos(reorderTodosRequest)
    }
}