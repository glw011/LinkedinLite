package ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.views.home.postShape

val CARD_SHAPE = RoundedCornerShape(16.dp)

/**
 * A card to be placed on a profile page that displays a list of members with
 * their names and roles.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param members A list of member names.
 * @param roles A list of roles corresponding to the members. Should be the same size as members.
 * @param modifier Modifier to apply to the card.
 */
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

/**
 * A card to be placed on a profile page that displays a list of tags/interests.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param modifier Modifier to apply to the card.
 */
@Composable
fun ProfileTagsCard(
    title: String,
    subtitle: String = "",
    tags: List<String> = emptyList(),
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

/**
 * A card to be placed on a profile page that displays a list of recent posts.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param modifier Modifier to apply to the card.
 */
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

/**
 * A generic card to be displayed on a profile page.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param isEditable Whether the card is editable or not. If editable, an edit button is shown.
 * @param onClick The callback for when the edit button is clicked.
 * @param modifier Modifier to apply to the card.
 * @param content The content to be displayed inside the card.
 */
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