package uz.gita.todo_john.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uz.gita.todo_john.R
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme
import uz.gita.todo_john.utils.TIME_PATTERN
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    pickedTime: LocalTime? = null,
    onDateChanged: (LocalTime?) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var pickedTimeState: LocalTime? by remember { mutableStateOf(pickedTime) }
    var timeText by remember {
        mutableStateOf(
            if (pickedTimeState == null) "Set time"
            else DateTimeFormatter.ofPattern(TIME_PATTERN).format(pickedTimeState)
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_time),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        TextButton(
            onClick = {
                dialogState.show()
            }
        ) {
            Text(text = timeText, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .alpha(if (pickedTime != null) 1f else 0f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ) {
                    timeText = "Set time"
                    pickedTimeState = null
                    onDateChanged(null)
                }
        )

        MaterialDialog(
            dialogState = dialogState,
            buttons = {
                positiveButton(text = "Ok", textStyle = MaterialTheme.typography.bodyLarge)

                negativeButton(text = "Cancel", textStyle = MaterialTheme.typography.bodyLarge)
            }
        ) {
            timepicker(
                initialTime = LocalTime.now(),
                title = "Pick time",
                colors = TimePickerDefaults.colors(
                    headerTextColor = MaterialTheme.colorScheme.onPrimary,
                    activeBackgroundColor = MaterialTheme.colorScheme.primary,
                    activeTextColor = MaterialTheme.colorScheme.onPrimary,
                    selectorColor = MaterialTheme.colorScheme.primary
                )
            ) {
                pickedTimeState = it
                onDateChanged(it)
                timeText = DateTimeFormatter.ofPattern(TIME_PATTERN).format(it)
            }
        }
    }
}

@Preview
@Composable
private fun TimePickerPreview() {
    ToDoTheme() {
        Surface {
            TimePicker() {
                Log.d("GGG", it.toString())
            }
        }
    }
}