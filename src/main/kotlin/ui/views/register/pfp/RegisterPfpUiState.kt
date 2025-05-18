package ui.views.register.pfp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap

/**
 * UI state for the RegisterInfo screen.
 *
 * @property profilePicture The profile picture selected by the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterPfpUIState(
    val errorMessage: String? = null,
) {
    var profilePicture by mutableStateOf(ImageBitmap(0, 0))
}