package uz.gita.todo_john.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogButtons
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uz.gita.todo_john.R
import uz.gita.todo_john.data.model.PriorityEnum
import uz.gita.todo_john.data.model.TaskData
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme
import uz.gita.todo_john.utils.DATE_PATTERN
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    pickedDate: LocalDate? = null,
    onDateChanged: (LocalDate?) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    var pickedDateState: LocalDate? by remember { mutableStateOf(pickedDate) }
    var dateText by remember {
        mutableStateOf(
            if (pickedDateState == null) "Set due date"
            else (DateTimeFormatter.ofPattern(DATE_PATTERN).format(pickedDateState))
        )
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_calendar),
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )

        TextButton(
            onClick = {
                dialogState.show()
            }
        ) {
            Text(text = dateText, style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .alpha(if (pickedDate != null) 1f else 0f)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = false)
                ) {
                    dateText = "Set due date"
                    pickedDateState = null
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
            datepicker(
                initialDate = LocalDate.now(),
                title = "Pick a date",
                colors = DatePickerDefaults.colors(
                    headerBackgroundColor = MaterialTheme.colorScheme.primary,
                    headerTextColor = MaterialTheme.colorScheme.onPrimary,
                    dateActiveBackgroundColor = MaterialTheme.colorScheme.primary,
                    dateActiveTextColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                pickedDateState = it
                onDateChanged(it)
                dateText = DateTimeFormatter.ofPattern(DATE_PATTERN).format(it)
            }
        }
    }
}

@Preview
@Composable
fun DatePickerPreview() {
    ToDoTheme() {
        Surface() {
            DatePicker() {
                Log.d("GGG", it.toString())
            }
        }
    }
}