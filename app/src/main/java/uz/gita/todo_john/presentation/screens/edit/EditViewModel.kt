package uz.gita.todo_john.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todo_john.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val directions: EditDirections
) : EditContract.ViewModel, ViewModel() {
    override val container = container<EditContract.UIState, EditContract.SideEffect>(EditContract.UIState.InitState)

    override fun onEventDispatcher(intent: EditContract.Intent) {
        when (intent) {
            is EditContract.Intent.EditTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appRepository.updateTask(intent.taskData)
                    directions.popBack()
                }
            }

            EditContract.Intent.PopBack -> {
                viewModelScope.launch {
                    directions.popBack()
                }
            }
        }
    }
}