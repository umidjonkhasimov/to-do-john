package uz.gita.todo_john.presentation.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.todo_john.presentation.ui.theme.ToDoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
    text: String = "",
    onTextChange: (String) -> Unit,

    ) {
    var textState by remember { mutableStateOf(text) }
    val placeholderState by remember { mutableStateOf(placeholder) }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        value = textState,
        enabled = true,
        onValueChange = { newText ->
            textState = newText
            onTextChange(newText)
        },
        label = { Text(text = placeholderState, style = MaterialTheme.typography.bodyLarge) },
        textStyle = MaterialTheme.typography.bodyLarge,
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun MyTextFieldPreview() {
    ToDoTheme() {
        Surface {
            MyTextField(placeholder = "Placeholder", text = "") {
                Log.d("GGG", "MyTextFieldPreview: $it")
            }
        }
    }
}