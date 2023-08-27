package uz.gita.todo_john.presentation.screens.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.todo_john.R
import uz.gita.todo_john.data.model.FilterEnum
import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.navigation.AppScreen
import uz.gita.todo_john.presentation.dialogs.CustomDialog
import uz.gita.todo_john.presentation.components.EmptyListPlaceholder
import uz.gita.todo_john.presentation.components.TaskComponent

class HomeScreen : AppScreen() {

    @Composable
    override fun Content() {
        val viewModel: HomeContract.ViewModel = getViewModel<HomeViewModel>()
        val uiState: HomeContract.UIState = viewModel.collectAsState().value

        HomeScreenContent(uiState = uiState, onEventDispatcher = viewModel::onEventDispatcher)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    uiState: HomeContract.UIState,
    onEventDispatcher: (HomeContract.Intent) -> Unit
) {
    when (uiState) {
        HomeContract.UIState.Loading -> {

        }

        is HomeContract.UIState.ReadyData -> {
            val taskList = uiState.taskList
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            var allSelected by remember { mutableStateOf(true) }
            var todaySelected by remember { mutableStateOf(false) }
            var thisWeekSelected by remember { mutableStateOf(false) }
            var prioritizedSelected by remember { mutableStateOf(false) }
            var filter by remember { mutableStateOf(FilterEnum.ALL) }

            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    ModalDrawerSheet(drawerShape = RectangleShape) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(MaterialTheme.colorScheme.tertiary),
                            contentAlignment = Center
                        ) {
                            Row(modifier = Modifier.wrapContentSize(), verticalAlignment = CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_checkmark),
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "To-Do",
                                    style = MaterialTheme.typography.titleLarge,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        }

                        Text(text = "Filters", fontSize = 12.sp, modifier = Modifier.padding(12.dp))

                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedTextColor = MaterialTheme.colorScheme.onTertiary,
                                selectedIconColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            icon = { Icon(imageVector = Icons.Default.Menu, contentDescription = null) },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            label = { Text(text = "All") },
                            selected = allSelected,
                            onClick = {
                                filter = FilterEnum.ALL
                                allSelected = true
                                todaySelected = false
                                thisWeekSelected = false
                                prioritizedSelected = false
                                scope.launch {
                                    drawerState.close()
                                }
                                onEventDispatcher(HomeContract.Intent.FilterTasks(filter))
                            }
                        )

                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedTextColor = MaterialTheme.colorScheme.onTertiary,
                                selectedIconColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_today), contentDescription = null) },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            label = { Text(text = "Today") },
                            selected = todaySelected,
                            onClick = {
                                filter = FilterEnum.TODAY
                                allSelected = false
                                todaySelected = true
                                thisWeekSelected = false
                                prioritizedSelected = false
                                scope.launch {
                                    drawerState.close()
                                }
                                onEventDispatcher(HomeContract.Intent.FilterTasks(filter))
                            }
                        )

                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedTextColor = MaterialTheme.colorScheme.onTertiary,
                                selectedIconColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_calendar), contentDescription = null) },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            label = { Text(text = "This week") },
                            selected = thisWeekSelected,
                            onClick = {
                                filter = FilterEnum.THIS_WEEK
                                allSelected = false
                                todaySelected = false
                                thisWeekSelected = true
                                prioritizedSelected = false
                                scope.launch {
                                    drawerState.close()
                                }
                                onEventDispatcher(HomeContract.Intent.FilterTasks(filter))
                            }
                        )
                        NavigationDrawerItem(
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                                selectedTextColor = MaterialTheme.colorScheme.onTertiary,
                                selectedIconColor = MaterialTheme.colorScheme.onTertiary
                            ),
                            icon = { Icon(painter = painterResource(id = R.drawable.ic_priority), contentDescription = null) },
                            modifier = Modifier.padding(horizontal = 12.dp),
                            label = { Text(text = "Prioritized") },
                            selected = prioritizedSelected,
                            onClick = {
                                filter = FilterEnum.PRIORITIZED
                                allSelected = false
                                todaySelected = false
                                thisWeekSelected = false
                                prioritizedSelected = true
                                scope.launch {
                                    drawerState.close()
                                }
                                onEventDispatcher(HomeContract.Intent.FilterTasks(filter))
                            }
                        )
                    }
                }
            ) {
                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .background(MaterialTheme.colorScheme.tertiary)
                                .padding(12.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            Image(
                                imageVector = Icons.Default.Menu,
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary),
                                modifier = Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(bounded = false)
                                ) {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )

                            Text(
                                text = "To-do list",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier
                                    .weight(1f),
                                textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onTertiary
                            )

                            Spacer(modifier = Modifier.size(24.dp))
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = RoundedCornerShape(100),
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            onClick = {
                                onEventDispatcher(HomeContract.Intent.OpenAddScreen)
                            }
                        ) {
                            Image(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add",
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary),
                            )
                        }
                    },
                ) { padding ->
                    if (taskList.isEmpty()) {
                        EmptyListPlaceholder()
                    } else {
                        var showDialog by remember { mutableStateOf(false) }
                        var currentData by remember { mutableStateOf<TaskData?>(null) }

                        LazyColumn(modifier = Modifier.padding(padding)) {
                            items(
                                count = taskList.size,
                                key = {
                                    taskList[it].id
                                }
                            ) { index ->
                                TaskComponent(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                    taskData = taskList[index],
                                    onItemClick = { taskData ->
                                        onEventDispatcher(HomeContract.Intent.OpenEditScreen(taskData))
                                    },
                                    onItemLongClick = { taskData ->
                                        currentData = taskData
                                        showDialog = true
                                    },
                                    onCheckboxClick = {
                                        onEventDispatcher(HomeContract.Intent.CheckTask(it, filter))
                                    }
                                )

                                CustomDialog(
                                    modifier = Modifier.padding(16.dp),
                                    taskData = currentData,
                                    showDialog = showDialog,
                                    onDeleteDialog = { taskData ->
                                        onEventDispatcher(HomeContract.Intent.DeleteTask(taskData))
                                        showDialog = false
                                    },
                                    onDismissDialog = {
                                        showDialog = false
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}