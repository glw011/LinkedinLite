package ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.views.exampleTags
import ui.views.postShape

val CARD_SHAPE = RoundedCornerShape(16.dp)

@Composable
fun ProfileTagsCard(
    title: String,
    subtitle: String = "",
    modifier: Modifier = Modifier
) {
    ProfileCard(
        title = title,
        subtitle = subtitle,
        modifier = modifier
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(minSize = 40.dp),
        ) {
            items(exampleTags.size) { index ->
                AssistChip(
                    // TODO: Click brings you to search page with tag filter
                    onClick = { /* Handle tag click */ },
                    label = { Text(
                        text = exampleTags[index],
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

@Composable
fun ProfilePostsCard(
    title: String,
    subtitle: String = "",
    modifier: Modifier = Modifier
) {
    ProfileCard(
        title = title,
        subtitle = subtitle,
        modifier = modifier
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            // Temporary: To be replaced by ProfilePosts
            items (3) { index ->
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(400.dp)
                        .padding(horizontal = 4.dp)
                        .clip(postShape)
                        .border(1.dp, Color.Gray, postShape),
                ) {
                    Text(
                        text = "Post ${index + 1}",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier
            .border(1.dp, Color.Gray, CARD_SHAPE)
            .clip(CARD_SHAPE),
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
}