package uz.gita.todo_john.domain.repository.impl

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import uz.gita.todo_john.data.local.room.converters.DateConverter
import uz.gita.todo_john.data.local.room.dao.TaskDao
import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.domain.repository.AppRepository
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val db: TaskDao
) : AppRepository {

    override fun getAllTasks(): Flow<List<TaskData>> {
        Log.d("GGG", "getAllTasks")

        return db.getAllTasks().map { list ->
            list.map { entity ->
                entity.toData()
            }
        }
    }

    override fun getAllToday(): Flow<List<TaskData>> {
        val now = LocalDateTime.now()
        val startOfDay = now.with(LocalTime.MIN)
        val endOfDay = now.with(LocalTime.MAX)

        Log.d("GGG", "getAllToday")

        val startDateString = DateConverter.fromDate(startOfDay)
        val endDateString = DateConverter.fromDate(endOfDay)

        return db.getAllBetweenDates(startDateString, endDateString).map { list ->
            list.map { task ->
                task.toData()
            }
        }
    }

    override fun getAllThisWeek(): Flow<List<TaskData>> {
        val now = LocalDateTime.now()
        val startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)).with(LocalTime.MAX)

        Log.d("GGG", "getAllThisWeek")

        val startDateString = DateConverter.fromDate(startOfWeek)
        val endDateString = DateConverter.fromDate(endOfWeek)

        return db.getAllBetweenDates(startDateString, endDateString).map { list ->
            list.map { task ->
                task.toData()
            }
        }
    }

    override fun getAllPrioritized(): Flow<List<TaskData>> {
        return db.getAllPrioritized().map { list ->
            list.map { task ->
                task.toData()
            }
        }
    }

    override fun insertTask(taskData: TaskData) = db.insertTask(taskData.toEntity())

    override fun deleteTask(taskData: TaskData) = db.deleteTask(taskData.toEntity())

    override fun updateTask(taskData: TaskData) = db.updateTask(taskData.toEntity())
}
