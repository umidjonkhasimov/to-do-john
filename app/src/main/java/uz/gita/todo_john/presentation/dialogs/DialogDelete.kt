package uz.gita.todo_john.presentation.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import uz.gita.todo_john.data.model.TaskData

@Composable
fun CustomDialog(
    modifier: Modifier = Modifier,
    taskData: TaskData?,
    showDialog: Boolean,
    onDeleteDialog: (TaskData) -> Unit,
    onDismissDialog: () -> Unit
) {
    if (showDialog && taskData != null) {
        Dialog(
            onDismissRequest = onDismissDialog
        ) {
            Card(
                modifier = modifier.wrapContentSize(),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                ) {
                    Text(
                        text = "Delete?",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = "You sure you want to delete this task?",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(
                            onClick = onDismissDialog,
                        ) {
                            Text(text = "No")
                        }

                        TextButton(
                            onClick = { onDeleteDialog(taskData) }
                        ) {
                            Text(text = "Yes")
                        }
                    }

                }
            }
        }
    }
}