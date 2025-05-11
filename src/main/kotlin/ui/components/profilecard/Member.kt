package ui.components.profilecard

import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_pfp
import util.getBitmapFromDrawableID

/**
 * Data class representing a member in a profile card.
 *
 * @property name The name of the member.
 * @property role The role of the member.
 * @property profilePicture The profile picture of the member. Defaults to a default image.
 */
data class Member(
    val name: String,
    var role: String,
    val profilePicture: ImageBitmap? = getBitmapFromDrawableID(Res.drawable.default_pfp),
)
