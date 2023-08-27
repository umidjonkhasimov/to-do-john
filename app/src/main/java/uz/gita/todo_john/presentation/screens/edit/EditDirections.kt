package uz.gita.todo_john.presentation.screens.edit

import uz.gita.todo_john.navigation.AppNavigation
import javax.inject.Inject

interface EditDirections {
    suspend fun popBack()
}

class EditDirectionsImpl @Inject constructor(
    private val navigation: AppNavigation
) : EditDirections {
    override suspend fun popBack() {
        navigation.popBack()
    }
}