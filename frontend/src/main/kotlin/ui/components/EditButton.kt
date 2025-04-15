package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun EditButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // Edit button UI
    Icon(
        imageVector = Icons.Default.Edit,
        contentDescription = "Edit",
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(CircleShape)
    )
}