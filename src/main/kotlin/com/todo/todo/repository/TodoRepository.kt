package com.todo.todo.repository

import com.todo.todo.entity.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface TodoRepository : JpaRepository<Todo, UUID> {
    fun findByUserId(userId: UUID): List<Todo>

    @Query(
        """
            WITH RECURSIVE ordered_todo(id) AS (
                SELECT id
                  FROM todo.todo_order
                 WHERE prev_todo IS NULL
                   AND user_id = :userId
                 UNION
                SELECT tOrder.id
                  FROM todo.todo_order tOrder
                  JOIN ordered_todo ON (tOrder.prev_todo = ordered_todo.id)
            )
            SELECT todo.*
              FROM ordered_todo ot
              JOIN todo.todo todo ON todo.id = ot.id
        """,
        nativeQuery = true,
    )
    fun findOrderedTodosByUserId(userId: UUID): List<Todo>
}