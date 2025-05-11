package ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

/**
 * A close button that can be used to dismiss dialogs or close actions.
 *
 * @param onClick The callback for when the button is clicked.
 * @param modifier Modifier to apply to the button.
 */
@Composable
fun CloseButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Edit Member",
        modifier = modifier
            .clip(CircleShape)
            .clickable(onClick = onClick),
        tint = Color.Red,
    )
}