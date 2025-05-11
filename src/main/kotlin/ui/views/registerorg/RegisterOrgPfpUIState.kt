package ui.views.registerorg

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_pfp
import util.getBitmapFromDrawableID

/**
 * UI state for the RegisterOrgPfp screen.
 *
 * @param profilePicture The profile picture of the organization. Nullable.
 */
data class RegisterOrgPfpUiState(
    var profilePicture: MutableState<ImageBitmap?> = mutableStateOf(getBitmapFromDrawableID(Res.drawable.default_pfp))
)