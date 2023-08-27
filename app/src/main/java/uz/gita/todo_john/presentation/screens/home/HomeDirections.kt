package uz.gita.todo_john.presentation.screens.home

import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.navigation.AppNavigation
import uz.gita.todo_john.navigation.AppScreen
import uz.gita.todo_john.presentation.screens.add.AddScreen
import uz.gita.todo_john.presentation.screens.edit.EditScreen
import javax.inject.Inject

interface HomeDirections {
    suspend fun openAddScreen()
    suspend fun openEditScreen(taskData: TaskData)
}

class HomeDirectionsImpl @Inject constructor(
    private val navigation: AppNavigation
) : HomeDirections {
    override suspend fun openAddScreen() {
        navigation.navigateTo(AddScreen())
    }

    override suspend fun openEditScreen(taskData: TaskData) {
        navigation.navigateTo(EditScreen(taskData))
    }
}