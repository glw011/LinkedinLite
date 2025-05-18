package ui.views.register.info

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * UI state for the RegisterInfo screen.
 *
 * @property name The name entered by the user.
 * @property surname The surname entered by the user.
 * @property schoolName The name of the school entered by the user.
 * @property tags A list of tags associated with the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterInfoUiState(
    var name: String = "",
    var surname: String = "",
    var schoolName: String = "",
    var major: String = "",
) {
    var errorMessage by mutableStateOf("")
    var tags = mutableStateListOf<String>()
}

/**
 * Sealed class representing the result of a registration attempt.
 */
sealed class RegisterInfoResult {
    data object Success : RegisterInfoResult()
    data class Error(val message: String) : RegisterInfoResult()
}

/**
 * Function to handle registration events and update the UI state accordingly.
 *
 * @param event The registration event to handle.
 * @param uiState The current UI state.
 * @return The result of the registration attempt.
 */
fun onRegisterInfoEvent(
    event: RegisterInfoEvent,
    uiState: RegisterInfoUiState,
) : RegisterInfoResult? {
    return when (event) {
        is RegisterInfoEvent.NameChanged -> { uiState.name = event.name; null }
        is RegisterInfoEvent.SurnameChanged -> { uiState.surname = event.surname; null }
        is RegisterInfoEvent.SchoolNameChanged -> { uiState.schoolName = event.schoolName; null }
        is RegisterInfoEvent.MajorChanged -> { uiState.major = event.major; null }
        is RegisterInfoEvent.TagAdded -> { uiState.tags.add(event.tag); null }
        is RegisterInfoEvent.TagRemoved -> { uiState.tags.remove(event.tag); null }
        is RegisterInfoEvent.OnContinue -> {
            when {
                uiState.name.isEmpty() -> RegisterInfoResult.Error("Please enter your name")
                uiState.schoolName.isEmpty() -> RegisterInfoResult.Error("Please select your school")
                else -> RegisterInfoResult.Success
            }
        }
        is RegisterInfoEvent.OnBack -> null
    }
}