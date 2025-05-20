package ui.views.login

sealed class LoginEvent {
    data object OnLogin : LoginEvent()
    data object OnRegister : LoginEvent()
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
}