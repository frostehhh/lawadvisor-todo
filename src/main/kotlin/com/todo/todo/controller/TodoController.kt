package com.todo.todo.controller

import com.todo.todo.dto.CreateTodoDto
import com.todo.todo.dto.UpdateTodoDto
import com.todo.todo.entity.Todo
import com.todo.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/todos")
class TodoController(
    private val todoService: TodoService,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodo(
        @RequestBody createTodoRequest: CreateTodoDto,
    ): Todo {
        return todoService.createTodo(createTodoRequest)
    }

    @GetMapping
    fun getTodos(
        @RequestParam(required = true) userId: UUID,
    ): List<Todo> {
        return todoService.getTodos(userId)
    }

    @PatchMapping("/{todoId}")
    fun updateTodo(
        @PathVariable todoId: UUID,
        @RequestBody updateTodoRequest: UpdateTodoDto,
    ): Todo {
        return todoService.updateTodo(todoId, updateTodoRequest)
    }

    @DeleteMapping("/{todoId}")
    fun deleteTodo(
        @PathVariable todoId: UUID,
    ) {
        return todoService.deleteTodo(todoId)
    }
}