package uz.gita.todo_john.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.todo_john.data.model.TaskData

interface AppRepository {
    fun getAllTasks(): Flow<List<TaskData>>
    fun insertTask(taskData: TaskData)
    fun deleteTask(taskData: TaskData)
    fun updateTask(taskData: TaskData)
    fun getAllToday(): Flow<List<TaskData>>
    fun getAllPrioritized(): Flow<List<TaskData>>
    fun getAllThisWeek(): Flow<List<TaskData>>
}