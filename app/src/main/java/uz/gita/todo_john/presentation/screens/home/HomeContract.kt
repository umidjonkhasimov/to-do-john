package uz.gita.todo_john.presentation.screens.home

import org.orbitmvi.orbit.ContainerHost
import uz.gita.todo_john.data.model.FilterEnum
import uz.gita.todo_john.data.model.TaskData

interface HomeContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object Loading : UIState
        data class ReadyData(val taskList: List<TaskData>) : UIState
    }

    sealed interface SideEffect {
        data class MakeToast(val message: String) : SideEffect
    }

    sealed interface Intent {
        object OpenAddScreen : Intent
        data class OpenEditScreen(val taskData: TaskData) : Intent
        data class CheckTask(val taskData: TaskData, val filter: FilterEnum) : Intent
        data class DeleteTask(val taskData: TaskData) : Intent
        data class FilterTasks(val filter: FilterEnum) : Intent
        data class SearchTask(val searchQuery: String) : Intent
    }
}