package ui.components.profilecard

import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import util.getBitmapFromDrawableID

data class Member(
    val name: String,
    var role: String,
    val profilePicture: ImageBitmap? = getBitmapFromDrawableID(Res.drawable.default_pfp),
)
