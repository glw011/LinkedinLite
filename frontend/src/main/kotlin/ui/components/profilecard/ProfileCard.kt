package ui.components.profilecard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import ui.components.EditButton

val CARD_SHAPE = RoundedCornerShape(16.dp)

/**
 * A generic card to be displayed on a profile page.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param isEditable Whether the card is editable or not. If editable, an edit button is shown.
 * @param onClick The callback for when the edit button is clicked.
 * @param editIcon The icon to be displayed on the edit button.
 * @param modifier Modifier to apply to the card.
 * @param content The content to be displayed inside the card.
 */
@Composable
fun ProfileCard(
    title: String,
    subtitle: String,
    isEditable: Boolean = true,
    onClick: () -> Unit = {},
    editIcon: ImageVector = Icons.Default.Edit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, CARD_SHAPE)
            .clip(CARD_SHAPE),
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                )
                Text(
                    text = subtitle,
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth(),
                )
                content()
            }
        }
        if (isEditable) {
            EditButton(
                onClick = onClick,
                icon = editIcon,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(24.dp)
                    .clip(CARD_SHAPE)
                    .align(Alignment.TopEnd)
            )
        }
    }
}