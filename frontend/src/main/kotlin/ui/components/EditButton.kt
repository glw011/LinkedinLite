package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Creates an edit button indicated by a pencil icon.
 *
 * @param onClick The callback for when the button is clicked.
 * @param modifier The modifier for the button.
 */
@Composable
fun EditButton(
    onClick: () -> Unit,
    icon: ImageVector = Icons.Default.Edit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = icon,
        contentDescription = "Edit",
        tint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
            .clickable(onClick = onClick)
            .clip(CircleShape)
    )
}