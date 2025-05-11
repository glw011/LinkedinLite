package ui.components.profilecard

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A card to be placed on a profile page that displays a list of tags/interests.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param onEdit Callback for when the edit button is clicked.
 * @param tags The list of tags to be displayed in the card.
 * @param modifier Modifier to apply to the card.
 */
@Composable
fun ProfileTagsCard(
    title: String,
    subtitle: String = "",
    onEdit: () -> Unit = {},
    tags: List<String> = emptyList(),
    modifier: Modifier = Modifier
) {
    ProfileCard(
        title = title,
        subtitle = subtitle,
        onClick = onEdit,
        modifier = modifier
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 40.dp),
        ) {
            items(tags.size) { index ->
                AssistChip(
                    // TODO: Click brings you to search page with tag filter
                    onClick = { /* Handle tag click */ },
                    label = { Text(
                        text = tags[index],
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.wrapContentHeight().widthIn(min=150.dp)
                    ) },
                    modifier = Modifier.padding(4.dp),
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        labelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}