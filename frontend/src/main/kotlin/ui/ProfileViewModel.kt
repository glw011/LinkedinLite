package ui

import androidx.compose.ui.graphics.ImageBitmap
import data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileViewModel {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setProfilePicture(imageBitmap: ImageBitmap?) {
        _uiState.value = _uiState.value.copy(profilePicture = imageBitmap)
    }
    fun setBanner(imageBitmap: ImageBitmap?) {
        _uiState.value = _uiState.value.copy(banner = imageBitmap)
    }
}