package uz.gita.todo_john.presentation.screens.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todo_john.domain.repository.AppRepository
import javax.inject.Inject


@HiltViewModel
class AddViewModel @Inject constructor(
    private val addDirections: AddDirections,
    private val appRepository: AppRepository
) : AddContract.ViewModel, ViewModel() {
    override val container = container<AddContract.UIState, AddContract.SideEffect>(AddContract.UIState.InitState)

    override fun onEventDispatcher(intent: AddContract.Intent) {
        when (intent) {
            is AddContract.Intent.AddNewTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appRepository.insertTask(intent.taskData)
                    addDirections.popBack()
                }
            }

            AddContract.Intent.PopBack -> {
                viewModelScope.launch {
                    addDirections.popBack()
                }
            }
        }
    }
}