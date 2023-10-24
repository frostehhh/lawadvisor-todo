package com.todo.todo.service

import com.todo.todo.dto.ReorderTodoEntry
import com.todo.todo.dto.ReorderTodosDto
import com.todo.todo.entity.TodoOrder
import com.todo.todo.repository.TodoOrderRepository
import com.todo.todo.utils.ServiceApiErrorCode
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TodoOrderService(
    private val todoOrderRepository: TodoOrderRepository,
) {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(TodoOrderService::class.java)
    }

    @Transactional(rollbackFor = [Exception::class])
    fun createTodoOrder(todoId: UUID, userId: UUID): TodoOrder {
        LOGGER.info("Creating todo order: todoId - {}, userId - {}", todoId, userId)
        val lastTodoOrder = todoOrderRepository.findByUserIdAndNextTodoIsNull(userId)

        return if (lastTodoOrder != null) {
            val createdTodoOrder = TodoOrder(
                id = todoId,
                userId = userId,
            )
            moveTodoOrderToPosition(
                createdTodoOrder,
                ReorderTodoEntry(createdTodoOrder.id, lastTodoOrder.id),
                userId
            )
        } else {
            todoOrderRepository.save(
                TodoOrder(
                    id = todoId,
                    userId = userId,
                )
            )
        }.also {
            LOGGER.info("Created TodoOrder: {}", it)
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun reorderTodos(reorderTodoRequest: ReorderTodosDto) {
        LOGGER.info("Reordering todos: request - {}", reorderTodoRequest)

        reorderTodoRequest.positions.forEach { position ->
            val currTodoOrder = getTodoOrder(position.id)
            unlinkTodoOrderFromAdjacentNodes(currTodoOrder)
            moveTodoOrderToPosition(currTodoOrder, position, reorderTodoRequest.userId)
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    fun deleteTodoOrder(id: UUID) {
        val todoOrder = getTodoOrder(id)
        unlinkTodoOrderFromAdjacentNodes(todoOrder)
        todoOrderRepository.delete(todoOrder)
    }

    @Transactional
    private fun unlinkTodoOrderFromAdjacentNodes(todoOrder: TodoOrder) {
        val prevTodoOrder = todoOrder?.prevTodo?.let { getTodoOrder(it)}
        val nextTodoOrder = todoOrder?.nextTodo?.let { getTodoOrder(it) }
        if (prevTodoOrder != null) {
            todoOrderRepository.save(
                prevTodoOrder.copy(
                    nextTodo = todoOrder.nextTodo
                )
            )
        }
        if (nextTodoOrder != null) {
            todoOrderRepository.save(
                nextTodoOrder.copy(
                    prevTodo = todoOrder.prevTodo
                )
            )
        }
    }

    @Transactional(rollbackFor = [Exception::class])
    private fun moveTodoOrderToPosition(todoOrder: TodoOrder, position: ReorderTodoEntry, userId: UUID): TodoOrder {
        val newPrevTodoOrder = position.prevTodo?.let {getTodoOrder(it)}
        val newNextTodoOrder = if (position.prevTodo == null) {
            todoOrderRepository.findByUserIdAndPrevTodoIsNull(userId)
        } else {
            newPrevTodoOrder?.nextTodo?.let { getTodoOrder(it) }
        }

        if (newPrevTodoOrder != null) {
            todoOrderRepository.save(
                newPrevTodoOrder.copy(
                    nextTodo = position.id
                )
            )
        }
        if (newNextTodoOrder != null) {
            todoOrderRepository.save(
                newNextTodoOrder.copy(
                    prevTodo = position.id
                )
            )
        }

        return todoOrderRepository.save(
            todoOrder.copy(
                prevTodo = newPrevTodoOrder?.id,
                nextTodo = newNextTodoOrder?.id,
            )
        )
    }

    private fun getTodoOrder(id: UUID): TodoOrder {
        return todoOrderRepository.findByIdOrNull(id)
            ?: throw ServiceApiErrorCode.TODO_NOT_FOUND.toException(
                data = mapOf("todoId" to id)
            )
    }
}