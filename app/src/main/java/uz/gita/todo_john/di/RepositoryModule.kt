package uz.gita.todo_john.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todo_john.domain.repository.AppRepository
import uz.gita.todo_john.domain.repository.impl.AppRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAppRepo(impl: AppRepositoryImpl): AppRepository

}