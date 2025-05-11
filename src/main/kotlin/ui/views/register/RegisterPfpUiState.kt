package ui.views.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_pfp
import util.getBitmapFromDrawableID

/**
 * UI state for the RegisterInfo screen.
 *
 * @property profilePicture The profile picture selected by the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterPfpUIState(
    var profilePicture: MutableState<ImageBitmap?> = mutableStateOf(getBitmapFromDrawableID(Res.drawable.default_pfp)),
    val errorMessage: String? = null,
)