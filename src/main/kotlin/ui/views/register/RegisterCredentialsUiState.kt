package ui.views.register

/**
 * Data class representing the UI state for the RegisterCredentials screen.
 *
 * @property email The email entered by the user.
 * @property password The password entered by the user.
 * @property confirmPassword The password confirmation entered by the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterCredentialsUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val errorMessage: String? = null,
)