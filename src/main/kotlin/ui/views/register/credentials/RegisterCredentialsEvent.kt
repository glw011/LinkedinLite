package ui.views.register.credentials

sealed class RegisterCredentialsEvent {
    data object OnContinue : RegisterCredentialsEvent()
    data object OnBack : RegisterCredentialsEvent()
    data class EmailChanged(val email: String) : RegisterCredentialsEvent()
    data class PasswordChanged(val password: String) : RegisterCredentialsEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegisterCredentialsEvent()
}