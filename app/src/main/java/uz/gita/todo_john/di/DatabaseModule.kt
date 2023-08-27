package uz.gita.todo_john.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.todo_john.data.local.room.dao.TaskDao
import uz.gita.todo_john.data.local.room.database.TaskDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TaskDatabase =
        Room
            .databaseBuilder(context, TaskDatabase::class.java, "task_database.db")
            .build()

    @Provides
    @Singleton
    fun provideDao(db: TaskDatabase): TaskDao = db.getDao()
}