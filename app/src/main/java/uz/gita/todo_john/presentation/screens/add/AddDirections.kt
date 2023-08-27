package uz.gita.todo_john.presentation.screens.add

import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.navigation.AppNavigation
import uz.gita.todo_john.presentation.screens.edit.EditScreen
import javax.inject.Inject

interface AddDirections {
    suspend fun popBack()
}

class AddDirectionsImpl @Inject constructor(
    private val navigation: AppNavigation
) : AddDirections {
    override suspend fun popBack() {
        navigation.popBack()
    }
}