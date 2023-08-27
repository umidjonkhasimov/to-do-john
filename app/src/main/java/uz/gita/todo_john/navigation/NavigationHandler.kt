package uz.gita.todo_john.navigation

import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.flow.SharedFlow

typealias NavArgs = Navigator.() -> Unit

interface NavigationHandler {
    val navigationBuffer: SharedFlow<NavArgs>
}