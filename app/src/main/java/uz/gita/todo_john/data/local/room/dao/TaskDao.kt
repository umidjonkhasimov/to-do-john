package uz.gita.todo_john.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import uz.gita.todo_john.data.local.room.entity.TaskEntity
import java.time.LocalDateTime

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(taskEntity: TaskEntity)

    @Delete
    fun deleteTask(taskEntity: TaskEntity)

    @Update
    fun updateTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks_table WHERE priority IS NOT NULL")
    fun getAllPrioritized(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks_table WHERE dueDate BETWEEN :startDate AND :endDate")
    fun getAllBetweenDates(startDate: String, endDate: String): Flow<List<TaskEntity>>
}