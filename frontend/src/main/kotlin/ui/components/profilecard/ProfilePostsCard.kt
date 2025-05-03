package ui.components.profilecard

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.views.home.postShape

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