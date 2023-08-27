package uz.gita.todo_john.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import uz.gita.todo_john.data.local.room.converters.DateConverter
import uz.gita.todo_john.data.model.PriorityEnum
import uz.gita.todo_john.data.model.TaskData
import java.time.LocalDateTime

@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val priority: PriorityEnum? = null,
    val isDone: Boolean = false,
    val dueDate: LocalDateTime,
    val onTrash: Boolean = false,
    val description: String,
    val createdTime: LocalDateTime
) {
    fun toData(): TaskData =
        TaskData(
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