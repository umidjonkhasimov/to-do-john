package uz.gita.todo_john.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationDispatcher @Inject constructor() : AppNavigation, NavigationHandler {
    override val navigationBuffer = MutableSharedFlow<NavArgs>()

    private suspend fun navigate(args: NavArgs) {
        navigationBuffer.emit(args)
    }

    override suspend fun navigateTo(screen: AppScreen) = navigate { push(screen) }

    override suspend fun replace(screen: AppScreen) = navigate { replace(screen) }

    override suspend fun popBack() = navigate { pop() }
}