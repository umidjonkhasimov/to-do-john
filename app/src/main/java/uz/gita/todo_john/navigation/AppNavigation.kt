package uz.gita.todo_john.navigation

import cafe.adriel.voyager.androidx.AndroidScreen

typealias AppScreen = AndroidScreen

interface AppNavigation {
    suspend fun navigateTo(screen: AppScreen)
    suspend fun replace(screen: AppScreen)
    suspend fun popBack()
}