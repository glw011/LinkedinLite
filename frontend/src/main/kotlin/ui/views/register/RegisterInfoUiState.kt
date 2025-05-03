package ui.views.register

import androidx.compose.runtime.mutableStateListOf

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
    val tags: MutableList<String> = mutableStateListOf(),
    val errorMessage: String? = null,
)