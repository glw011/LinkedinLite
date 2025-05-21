package ui.components.profilecard

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Data class representing a member in a profile card.
 *
 * @property name The name of the member.
 * @property role The role of the member.
 * @property profilePicture The profile picture of the member. Defaults to a default image.
 */
data class Associate(
    val name: String,
    var role: String,
    val profilePicture: ImageBitmap = ImageBitmap(0, 0),
)
