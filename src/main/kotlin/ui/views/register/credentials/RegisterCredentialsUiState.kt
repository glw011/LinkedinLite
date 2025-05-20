package ui.views.register.credentials

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import service.OrgService
import service.StudentService

val debug = false

/**
 * Data class representing the UI state for the RegisterCredentials screen.
 *
 * @property email The email entered by the user.
 * @property password The password entered by the user.
 * @property confirmPassword The password confirmation entered by the user.
 * @property errorMessage An optional error message to display.
 */
data class RegisterCredentialsUiState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",
) {
    var errorMessage by mutableStateOf("")
}

sealed class RegisterCredentialsResult {
    data object Success : RegisterCredentialsResult()
    data class Error(val message: String) : RegisterCredentialsResult()
}

fun onRegisterCredentialsEvent(
    event: RegisterCredentialsEvent,
    uiState: RegisterCredentialsUiState,
): RegisterCredentialsResult? {
    return when (event) {
        is RegisterCredentialsEvent.EmailChanged -> { uiState.email = event.email; null }
        is RegisterCredentialsEvent.PasswordChanged -> { uiState.password = event.password; null }
        is RegisterCredentialsEvent.ConfirmPasswordChanged -> { uiState.confirmPassword = event.confirmPassword; null }
        is RegisterCredentialsEvent.OnContinue -> {
            if (!debug) {
                val students = StudentService.getAllStudents().toList()
                val organizations = OrgService.getAllOrgs().values.toList()
                val emails = students.map { it.email.lowercase() } + organizations.map { it.email }
                when {
                    uiState.password != uiState.confirmPassword -> RegisterCredentialsResult.Error("Passwords do not match")
                    uiState.email.isEmpty() || uiState.password.isEmpty() -> RegisterCredentialsResult.Error(
                        "Email and password cannot be empty"
                    )
                    !uiState.email.contains("@") -> RegisterCredentialsResult.Error("Invalid email format")
                    !uiState.email.contains(".") -> RegisterCredentialsResult.Error("Invalid email format")
                    uiState.email.lowercase() in emails -> RegisterCredentialsResult.Error("Email already in use")
                    uiState.password.length < 8 -> RegisterCredentialsResult.Error("Password must be at least 8 characters long")
                    !uiState.password.any { it.isDigit() } -> RegisterCredentialsResult.Error("Password must contain at least one digit")
                    !uiState.password.any { it.isUpperCase() } -> RegisterCredentialsResult.Error("Password must contain at least one uppercase letter")
                    !uiState.password.any { it.isLowerCase() } -> RegisterCredentialsResult.Error("Password must contain at least one lowercase letter")
                    !uiState.password.any { "!@#$%^&*()_+[]{}|;':,.<>?`~".contains(it) } -> RegisterCredentialsResult.Error("Password must contain at least one special character")
                    else -> RegisterCredentialsResult.Success
                }
            } else {
                RegisterCredentialsResult.Success
            }
        }
        is RegisterCredentialsEvent.OnBack -> null
    }
}