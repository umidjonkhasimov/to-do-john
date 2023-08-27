package uz.gita.todo_john.presentation.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import uz.gita.todo_john.data.model.FilterEnum
import uz.gita.todo_john.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val homeDirections: HomeDirections
) : HomeContract.ViewModel, ViewModel() {
    override val container = container<HomeContract.UIState, HomeContract.SideEffect>(HomeContract.UIState.Loading)
    var job: Job? = null

    init {
        job = appRepository.getAllTasks().onEach {
            intent { reduce { HomeContract.UIState.ReadyData(it) } }
        }.launchIn(viewModelScope)
    }

    override fun onEventDispatcher(intent: HomeContract.Intent) {
        when (intent) {
            HomeContract.Intent.OpenAddScreen -> {
                viewModelScope.launch {
                    homeDirections.openAddScreen()
                }
            }

            is HomeContract.Intent.OpenEditScreen -> {
                viewModelScope.launch {
                    homeDirections.openEditScreen(intent.taskData)
                }
            }

            is HomeContract.Intent.DeleteTask -> {
                viewModelScope.launch(Dispatchers.IO) {
                    appRepository.deleteTask(intent.taskData)
                }
            }

            is HomeContract.Intent.SearchTask -> {
                val query = intent.searchQuery
            }

            is HomeContract.Intent.CheckTask -> {
                val filter = intent.filter
                val taskData = intent.taskData

                when (filter) {
                    FilterEnum.ALL -> {
                        job?.cancel()

                        job = viewModelScope.launch(Dispatchers.IO) {
                            appRepository.updateTask(taskData)

                            appRepository.getAllTasks().onEach {
                                intent { reduce { HomeContract.UIState.ReadyData(it) } }
                            }.collect()
                        }
                    }

                    FilterEnum.TODAY -> {
                        job?.cancel()
                        job = viewModelScope.launch(Dispatchers.IO) {
                            appRepository.updateTask(taskData)

                            appRepository.getAllToday().onEach {
                                intent { reduce { HomeContract.UIState.ReadyData(it) } }
                            }.collect()
                        }
                    }

                    FilterEnum.THIS_WEEK -> {
                        job?.cancel()
                        job = viewModelScope.launch(Dispatchers.IO) {
                            appRepository.updateTask(taskData)

                            appRepository.getAllThisWeek().onEach {
                                intent { reduce { HomeContract.UIState.ReadyData(it) } }
                            }.collect()
                        }
                    }

                    FilterEnum.PRIORITIZED -> {
                        job?.cancel()
                        job = viewModelScope.launch(Dispatchers.IO) {
                            appRepository.updateTask(taskData)

                            appRepository.getAllPrioritized().onEach {
                                intent { reduce { HomeContract.UIState.ReadyData(it) } }
                            }.collect()
                        }
                    }
                }
            }

            is HomeContract.Intent.FilterTasks -> {
                when (intent.filter) {
                    FilterEnum.ALL -> {
                        job?.cancel()
                        job = appRepository.getAllTasks().onEach {
                            intent { reduce { HomeContract.UIState.ReadyData(it) } }
                        }.launchIn(viewModelScope)
                    }

                    FilterEnum.TODAY -> {
                        job?.cancel()
                        job = appRepository.getAllToday().onEach {
                            intent { reduce { HomeContract.UIState.ReadyData(it) } }
                        }.launchIn(viewModelScope)
                    }

                    FilterEnum.THIS_WEEK -> {
                        job?.cancel()
                        job = appRepository.getAllThisWeek().onEach {
                            intent { reduce { HomeContract.UIState.ReadyData(it) } }
                        }.launchIn(viewModelScope)
                    }

                    FilterEnum.PRIORITIZED -> {
                        job?.cancel()
                        job = appRepository.getAllPrioritized().onEach {
                            intent { reduce { HomeContract.UIState.ReadyData(it) } }
                        }.launchIn(viewModelScope)
                    }
                }
            }
        }
    }
}