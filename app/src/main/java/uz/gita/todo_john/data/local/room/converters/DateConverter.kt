package uz.gita.todo_john.data.local.room.converters

import androidx.room.TypeConverter
import java.time.LocalDateTime

object DateConverter {
    @TypeConverter
    fun toDate(dateString: String): LocalDateTime = LocalDateTime.parse(dateString)

    @TypeConverter
    fun fromDate(date: LocalDateTime): String = date.toString()
}