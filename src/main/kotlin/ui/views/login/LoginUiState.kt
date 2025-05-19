package ui.views.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import service.UserService

data class LoginUiState(
    var email: String = "",
    var password: String = "",
) {
    var errorMessage by mutableStateOf("")
}

sealed class LoginResult {
    data object Success : LoginResult()
    data class Error(val message: String) : LoginResult()
}

fun onLoginEvent(event: LoginEvent, uiState: LoginUiState): LoginResult? {
    return when (event) {
        is LoginEvent.OnLogin -> {
            when {
                uiState.email.isEmpty() -> LoginResult.Error("Email cannot be empty")
                uiState.password.isEmpty() -> LoginResult.Error("Password cannot be empty")
                UserService().authenticate(uiState.email, uiState.password) != null -> {
                    LoginResult.Success
                }
                else -> {
                    LoginResult.Error("Invalid email or password")
                }
            }
        }
        is LoginEvent.OnRegister -> { null }
        is LoginEvent.EmailChanged -> { uiState.email = event.email; null }
        is LoginEvent.PasswordChanged -> { uiState.password = event.password; null }
    }
}