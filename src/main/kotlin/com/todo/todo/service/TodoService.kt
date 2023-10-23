package com.todo.todo.service

import com.todo.todo.dto.CreateTodoDto
import com.todo.todo.dto.UpdateTodoDto
import com.todo.todo.dto.toEntity
import com.todo.todo.entity.Todo
import com.todo.todo.repository.TodoRepository
import com.todo.todo.utils.ServiceApiErrorCode
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.util.*

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(TodoService::class.java)
    }

    fun createTodo(createTodoDto: CreateTodoDto): Todo {
        LOGGER.info("Received request to create todo: {}", createTodoDto)

        val currentTime = OffsetDateTime.now()
        return todoRepository.save(createTodoDto.toEntity(currentTime))
    }

    fun getTodos(userId: UUID?): List<Todo> {
        LOGGER.info("Received request to query todos: userId - {}", userId)

        return if (userId != null) {
            todoRepository.findByUserId(userId)
        } else {
            todoRepository.findAll()
        }
    }

    fun updateTodo(todoId: UUID, updateTodoDto: UpdateTodoDto): Todo {
        LOGGER.info(
            "Received request to update todo: todoId - {}, updateTodoDto - {}",
            todoId,
            updateTodoDto,
        )

        val updatedTodo = todoRepository.findByIdOrNull(todoId)?.let { todo ->
            updateTodoDto.toEntity(
                id = todo.id,
                userId = todo.userId,
                updatedOn = OffsetDateTime.now(),
                createdOn = todo.createdOn,
            )
        } ?: throw ServiceApiErrorCode.TODO_NOT_FOUND.toException(
            data = mapOf(
                "todoId" to todoId
            )
        )

        return todoRepository.saveAndFlush(updatedTodo)
            .also {
                LOGGER.info("Successfully updated todo: {}", it)
            }
    }
}