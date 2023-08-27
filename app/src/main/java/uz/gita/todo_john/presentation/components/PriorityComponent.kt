package uz.gita.todo_john.presentation.components

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.todo_john.data.model.PriorityEnum
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PriorityComponent(
    modifier: Modifier = Modifier,
    priorityEnum: PriorityEnum? = null,
    onPriorityClick: (PriorityEnum?) -> Unit
) {
    var low by remember { mutableStateOf(false) }
    var med by remember { mutableStateOf(false) }
    var high by remember { mutableStateOf(false) }

    when (priorityEnum) {
        PriorityEnum.LOW -> {
            low = true
        }

        PriorityEnum.MEDIUM -> {
            med = true
        }

        PriorityEnum.HIGH -> {
            high = true
        }

        null -> {

        }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(2.dp)) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(12),
                colors =
                CardDefaults.cardColors(
                    containerColor = if (low) MaterialTheme.colorScheme.tertiary else {
                        Color.Transparent
                    }
                ),
                onClick = {
                    if (low) {
                        low = false
                        med = false
                        high = false
                        onPriorityClick(null)
                    } else {
                        low = true
                        med = false
                        high = false
                        onPriorityClick(PriorityEnum.LOW)
                    }
                }
            ) {
                Text(
                    text = "Low",
                    color = if (low) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(8.dp),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(12),
                colors = CardDefaults.cardColors(
                    containerColor =
                    if (med) MaterialTheme.colorScheme.tertiary else {
                        Color.Transparent
                    }
                ),
                onClick = {
                    if (med) {
                        low = false
                        med = false
                        high = false
                        onPriorityClick(null)
                    } else {
                        med = true
                        low = false
                        high = false
                        onPriorityClick(PriorityEnum.MEDIUM)
                    }
                }
            ) {
                Text(
                    text = "Medium",
                    color = if (med) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(8.dp),
                    fontSize = 18.sp
                )
            }
            Card(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(12),
                colors =
                CardDefaults.cardColors(
                    containerColor =
                    if (high) MaterialTheme.colorScheme.tertiary else {
                        Color.Transparent
                    }
                ),
                onClick = {
                    if (high) {
                        low = false
                        med = false
                        high = false
                        onPriorityClick(null)
                    } else {
                        high = true
                        low = false
                        med = false
                        onPriorityClick(PriorityEnum.HIGH)
                    }
                }
            ) {
                Text(
                    text = "High",
                    color = if (high) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(8.dp),
                    fontSize = 18.sp
                )
            }
        }
    }

}


@Preview
@Composable
private fun PriorityComponentPreview() {
    val context = LocalContext.current
    ToDoTheme() {
        PriorityComponent() {
            Toast.makeText(context, it?.name, Toast.LENGTH_SHORT).show()
        }
    }
}