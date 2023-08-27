package uz.gita.todo_john.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.todo_john.navigation.AppNavigation
import uz.gita.todo_john.navigation.NavigationDispatcher
import uz.gita.todo_john.navigation.NavigationHandler
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @Binds
    @Singleton
    fun provideAppNavigation(impl: NavigationDispatcher): AppNavigation

    @Binds
    @Singleton
    fun provideNavigationHandler(impl: NavigationDispatcher): NavigationHandler
}