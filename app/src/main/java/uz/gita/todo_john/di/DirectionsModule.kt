package uz.gita.todo_john.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.todo_john.presentation.screens.add.AddDirections
import uz.gita.todo_john.presentation.screens.add.AddDirectionsImpl
import uz.gita.todo_john.presentation.screens.edit.EditDirections
import uz.gita.todo_john.presentation.screens.edit.EditDirectionsImpl
import uz.gita.todo_john.presentation.screens.home.HomeDirections
import uz.gita.todo_john.presentation.screens.home.HomeDirectionsImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionsModule {
    @Binds
    fun bindAddDirection(impl: AddDirectionsImpl): AddDirections

    @Binds
    fun bindHomeDirection(impl: HomeDirectionsImpl): HomeDirections

    @Binds
    fun bindEditDirection(impl: EditDirectionsImpl): EditDirections
}