package ui.views.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap

/**
 * UI state for the RegisterInfo screen.
 *
 * @property profilePicture The profile picture selected by the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterPfpUIState(
    var profilePicture: MutableState<ImageBitmap> = mutableStateOf(ImageBitmap(0, 0)),
    val errorMessage: String? = null,
)