package uz.gita.todo_john.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.todo_john.R

@Composable
fun EmptyListPlaceholder() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.tertiary)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Empty",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun EmptyListPlaceholderPreview() {
    EmptyListPlaceholder()
}