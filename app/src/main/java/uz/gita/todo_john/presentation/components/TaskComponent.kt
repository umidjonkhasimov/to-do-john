package uz.gita.todo_john.presentation.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.IndicationInstance
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.w3c.dom.Text
import uz.gita.todo_john.R
import uz.gita.todo_john.data.model.PriorityEnum
import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskComponent(
    modifier: Modifier = Modifier,
    taskData: TaskData,
    onItemClick: (TaskData) -> Unit,
    onItemLongClick: (TaskData) -> Unit,
    onCheckboxClick: (TaskData) -> Unit
) {
    var checkedState by remember { mutableStateOf(taskData.isDone) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .combinedClickable(
                onClick = { onItemClick(taskData) },
                onLongClick = { onItemLongClick(taskData) }
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
//        onClick = { onItemClick(taskData) }
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = checkedState, onCheckedChange = {
                onCheckboxClick(taskData.copy(isDone = it))
                checkedState = !checkedState
            })

            Text(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .width(150.dp),
                text = taskData.title,
                style = if (checkedState) {
                    MaterialTheme.typography.labelSmall
                } else {
                    MaterialTheme.typography.bodyLarge
                },
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            if (taskData.priority != null) {
                Image(
                    painter = painterResource(id = R.drawable.ic_priority),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                )
            }

            DueDate(date = taskData.dueDate, modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}

@Composable
fun DueDate(
    date: LocalDateTime,
    modifier: Modifier = Modifier
) {
    val formattedTime by remember { mutableStateOf(date.format(DateTimeFormatter.ofPattern("EEE, h a"))) }
    Card(
        modifier = modifier.wrapContentSize(),
        shape = RoundedCornerShape(100F),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            modifier = Modifier
                .padding(6.dp),
            text = formattedTime, fontSize = 10.sp
        )
    }
}

@Preview
@Composable
private fun TaskComponentPrev() {
    val context = LocalContext.current

    val tomorrow: LocalDateTime = LocalDateTime.now().plus(1, ChronoUnit.DAYS)
    val futureDate: LocalDateTime = LocalDateTime.of(tomorrow.toLocalDate(), LocalTime.of(8, 0))
    ToDoTheme {
        TaskComponent(
            modifier = Modifier.padding(12.dp),
            taskData = TaskData(
                0,
                "aasaf",
                PriorityEnum.LOW,
                isDone = false,
                futureDate,
                false,
                "clicked",
                futureDate
            ),
            onItemClick = {

            },
            onItemLongClick = {

            },
            onCheckboxClick = {
//                Toast.makeText(context, "${(it.dueDate?.dayOfWeek?.name)?.substring(0, 3)}, ${it.dueDate.hour}", Toast.LENGTH_SHORT).show()
            }
        )
    }
}