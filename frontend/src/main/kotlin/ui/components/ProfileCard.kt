package ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.views.exampleMembers
import ui.views.exampleRoles
import ui.views.exampleTags
import ui.views.postShape

val CARD_SHAPE = RoundedCornerShape(16.dp)

@Composable
fun ProfileMembersCard(
    title: String,
    subtitle: String = "",
    members: List<String>,
    roles: List<String>,
    modifier: Modifier = Modifier
) {
    ProfileCard(
        title = title,
        subtitle = subtitle,
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(members.size) { index ->
                Column(
                    modifier = Modifier
                        .width(175.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable(onClick = {/* TODO: Handle click */ })
                        .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                ) {
                    PfpImage(
                        imageBitmap = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        contentScale = ContentScale.FillHeight
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(0.3f)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        ) {
                            if (index < members.size && index < roles.size) {
                                Text(
                                    text = members[index],
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                                Text(
                                    text = roles[index],
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

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
        isEditable = false,
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
    isEditable: Boolean = true,
    onClick: () -> Unit = {},
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
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(24.dp)
                    .clip(CARD_SHAPE)
                    .align(Alignment.TopEnd)
            )
        }
    }
}