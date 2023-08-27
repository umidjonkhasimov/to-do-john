package uz.gita.todo_john.presentation.screens.add

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import cafe.adriel.voyager.hilt.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import uz.gita.todo_john.data.model.PriorityEnum
import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.navigation.AppScreen
import uz.gita.todo_john.presentation.components.DatePicker
import uz.gita.todo_john.presentation.components.MyTextField
import uz.gita.todo_john.presentation.components.PriorityComponent
import uz.gita.todo_john.presentation.components.TimePicker
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme
import uz.gita.todo_john.utils.DUE_DATE_EXTRA
import uz.gita.todo_john.utils.ReminderWorkManager
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import kotlin.time.DurationUnit
import kotlin.time.toDuration


class AddScreen : AppScreen() {

    @Composable
    override fun Content() {
        val viewModel: AddContract.ViewModel = getViewModel<AddViewModel>()
        val uiState: AddContract.UIState = viewModel.collectAsState().value
        val context = LocalContext.current

        ToDoTheme {
            Surface {
                AddScreenContent(
                    uiState = uiState,
                    onEventDispatcher = viewModel::onEventDispatcher
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreenContent(
    uiState: AddContract.UIState,
    onEventDispatcher: (AddContract.Intent) -> Unit
) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDesc by remember { mutableStateOf("") }
    var taskPriority: PriorityEnum? by remember { mutableStateOf(null) }
    var pickedDate: LocalDate? by remember { mutableStateOf(null) }
    var pickedTime: LocalTime? by remember { mutableStateOf(null) }
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }

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
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary),
                    modifier = Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false)
                    ) {
                        onEventDispatcher(AddContract.Intent.PopBack)
                    }
                )

                Text(
                    text = "Add Task",
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
                    if (taskTitle.isEmpty()) {
                        Toast.makeText(context, "Title cannot be empty!", Toast.LENGTH_SHORT).show()
                    } else if (pickedDate == null) {
                        Toast.makeText(context, "Due date should be picked!", Toast.LENGTH_SHORT).show()
                    } else if (pickedTime == null) {
                        Toast.makeText(context, "Time should be picked!", Toast.LENGTH_SHORT).show()
                    } else {
                        val taskData = TaskData(
                            id = 0,
                            title = taskTitle,
                            priority = taskPriority,
                            dueDate = LocalDateTime.of(pickedDate, pickedTime),
                            description = taskDesc,
                            createdTime = LocalDateTime.now()
                        )
                        scheduleNotification(context, taskData)
                        onEventDispatcher(AddContract.Intent.AddNewTask(taskData))
                    }
                }
            ) {
                Image(
                    imageVector = Icons.Default.Done,
                    contentDescription = "Add",
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onTertiary),
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            MyTextField(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .focusRequester(focusRequester),
                placeholder = "What needs to be done?"
            ) { newText ->
                taskTitle = newText
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Priority",
                style = MaterialTheme.typography.bodyLarge
            )

            PriorityComponent(
                modifier = Modifier.padding(12.dp)
            ) { priority ->
                focusManager.clearFocus()
                taskPriority = priority
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Due Date",
                style = MaterialTheme.typography.bodyLarge
            )

            DatePicker(
                modifier = Modifier.padding(horizontal = 12.dp),
            ) {
                pickedDate = it
            }

            TimePicker(
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                pickedTime = it
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = "Description",
                style = MaterialTheme.typography.bodyLarge
            )

            BasicTextField(
                modifier = Modifier.padding(12.dp),
                value = taskDesc,
                onValueChange = { newText ->
                    taskDesc = newText
                },
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(12.dp)
                    ) {
                        innerTextField()
                    }

                }
            )
        }
    }
}

fun scheduleNotification(context: Context, taskData: TaskData) {
    val dueDate = taskData.dueDate // LocalDateTime.parse(taskData.dueDate.toString())
    val now = Instant.now()
    val future = dueDate.atZone(ZoneId.systemDefault()).toInstant()
    val delay = Duration.between(now, future).toMillis()

//    val duration = Duration.between(LocalDateTime.now(), dueDate)
//    val delay = duration.toMillis()
//    val delay = System.currentTimeMillis() - duration
    Log.d("GGG", "delay: $delay")

    val workManager = WorkManager.getInstance(context)
    val workRequest: OneTimeWorkRequest = OneTimeWorkRequest.Builder(ReminderWorkManager::class.java)
        .setInputData(Data.Builder().putString(DUE_DATE_EXTRA, taskData.title).build()) // Pass the dueDate as input data
        .setInitialDelay(delay, TimeUnit.MILLISECONDS) // Delay before the work is executed
        .build()

    workManager.enqueue(workRequest)
}

@Preview
@Composable
fun AddScreenContentPreview() {
    ToDoTheme() {
        Surface() {
            AddScreenContent(AddContract.UIState.InitState) {

            }
        }
    }
}