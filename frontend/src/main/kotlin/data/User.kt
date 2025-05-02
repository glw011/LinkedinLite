package data

import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import util.getBitmapFromDrawableID

data class User(
    val name: String,
    val email: String,
    val password: String,
    val profilePicture: ImageBitmap? = getBitmapFromDrawableID(Res.drawable.default_pfp),
    val bio: String = "",
    val location: String = "",
)
