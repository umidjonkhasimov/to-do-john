package uz.gita.todo_john.presentation.screens.edit

import org.orbitmvi.orbit.ContainerHost
import uz.gita.todo_john.data.model.TaskData

interface EditContract {
    interface ViewModel : ContainerHost<UIState, SideEffect> {
        fun onEventDispatcher(intent: Intent)
    }

    sealed interface UIState {
        object InitState : UIState
    }

    sealed interface SideEffect {
        data class MakeToast(val message: String) : SideEffect
    }

    sealed interface Intent {
        data class EditTask(val taskData: TaskData) : Intent
        object PopBack : Intent
    }
}