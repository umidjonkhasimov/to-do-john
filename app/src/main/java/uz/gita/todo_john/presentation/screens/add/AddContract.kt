package uz.gita.todo_john.presentation.screens.add

import org.orbitmvi.orbit.ContainerHost
import uz.gita.todo_john.data.model.TaskData

interface AddContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface SideEffect {
        data class MakeToast(val message: String) : SideEffect
    }

    sealed interface UIState {
        object InitState : UIState
    }

    sealed interface Intent {
        data class AddNewTask(val taskData: TaskData) : Intent
        object PopBack : Intent
    }
}