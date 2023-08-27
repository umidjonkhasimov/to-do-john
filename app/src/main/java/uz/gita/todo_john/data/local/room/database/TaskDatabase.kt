package uz.gita.todo_john.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.todo_john.data.local.room.converters.DateConverter
import uz.gita.todo_john.data.local.room.dao.TaskDao
import uz.gita.todo_john.data.local.room.entity.TaskEntity

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun getDao(): TaskDao
}