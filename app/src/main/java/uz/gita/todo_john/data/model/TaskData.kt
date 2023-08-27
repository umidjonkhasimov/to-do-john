package uz.gita.todo_john.data.model

import uz.gita.todo_john.data.local.room.entity.TaskEntity
import java.time.LocalDateTime
import java.util.Date

data class TaskData(
    val id: Int,
    val title: String,
    val priority: PriorityEnum? = null,
    val isDone: Boolean = false,
    val dueDate: LocalDateTime,
    val onTrash: Boolean = false,
    val description: String,
    val createdTime: LocalDateTime
) {
    fun toEntity(): TaskEntity =
        TaskEntity(
            id = id,
            title = title,
            priority = priority,
            isDone = isDone,
            dueDate = dueDate,
            onTrash = onTrash,
            description = description,
            createdTime = createdTime
        )
}