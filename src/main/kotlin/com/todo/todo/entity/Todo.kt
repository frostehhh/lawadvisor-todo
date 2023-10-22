package com.todo.todo.entity

import jakarta.persistence.*
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "todo", schema = "todo")
data class Todo(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),
    val userId: UUID,
    val title: String?,
    val details: String?,
    val createdOn: OffsetDateTime,
    val updatedOn: OffsetDateTime,
)