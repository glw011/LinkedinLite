package ui.views.register

import RegistrationInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import model.UserType
import ui.views.register.credentials.RegisterCredentialsEvent
import ui.views.register.credentials.RegisterCredentialsResult
import ui.views.register.credentials.RegisterCredentialsUiState
import ui.views.register.credentials.onRegisterCredentialsEvent
import ui.views.register.credentials.registerCredentialsScreen
import ui.views.register.info.RegisterInfoEvent
import ui.views.register.info.RegisterInfoResult
import ui.views.register.info.RegisterInfoUiState
import ui.views.register.info.onRegisterInfoEvent
import ui.views.register.info.registerInfoScreen
import ui.views.register.info.registerInfoScreenCont
import ui.views.register.pfp.RegisterPfpUIState
import ui.views.register.pfp.registerPfpScreen

enum class View {
    Select,
    Credentials,
    Info,
    Info2,
    Pfp,
}

@Composable
fun Register(
    registrationInfo: RegistrationInfo,
    onBack: () -> Unit,
    onRegister: () -> Unit,
) {
    var currentView by rememberSaveable { mutableStateOf(View.Select) }

    var registerCredentialsUiState by rememberSaveable {
        mutableStateOf(RegisterCredentialsUiState())
    }
    var registerInfoUiState by rememberSaveable {
        mutableStateOf(RegisterInfoUiState())
    }
    val registerPfpUiState by rememberSaveable {
        mutableStateOf(RegisterPfpUIState())
    }

    when (currentView) {
        View.Select -> registerSelectScreen(
            onPerson = {
                currentView = View.Credentials
                registrationInfo.accountType = UserType.STUDENT
                       },
            onOrg = {
                currentView = View.Credentials
                registrationInfo.accountType = UserType.ORG
                       },
            onBack = { onBack() }
        )
        View.Credentials -> {
            registerCredentialsScreen(
                uiState = registerCredentialsUiState,
                onEvent = { event ->
                    val result = onRegisterCredentialsEvent(event, registerCredentialsUiState)
                    if (event is RegisterCredentialsEvent.OnContinue) {
                        when (result) {
                            is RegisterCredentialsResult.Success -> {
                                currentView = View.Info
                                registrationInfo.email = registerCredentialsUiState.email
                                registrationInfo.password = registerCredentialsUiState.password
                                registerCredentialsUiState = RegisterCredentialsUiState()
                            }
                            is RegisterCredentialsResult.Error -> {
                                registerCredentialsUiState.errorMessage = result.message
                            }
                            null -> {}
                        }
                    } else if (event is RegisterCredentialsEvent.OnBack) {
                        currentView = View.Select
                        registerCredentialsUiState = RegisterCredentialsUiState()
                        registerInfoUiState = RegisterInfoUiState()
                    }
                })
        }
        View.Info -> registerInfoScreen(
            uiState = registerInfoUiState,
            onEvent = { event ->
                val result = onRegisterInfoEvent(event, registerInfoUiState)
                if (event is RegisterInfoEvent.OnContinue) {
                    when (result) {
                        is RegisterInfoResult.Success -> {
                            if (registrationInfo.accountType == UserType.STUDENT) {
                                currentView = View.Info2
                            } else {
                                currentView = View.Pfp
                            }
                            registrationInfo.name = registerInfoUiState.name
                            registrationInfo.surname = registerInfoUiState.surname
                            registrationInfo.schoolName = registerInfoUiState.schoolName
                            registrationInfo.major = registerInfoUiState.major
                            registrationInfo.tags = registerInfoUiState.tags
                            registerInfoUiState.errorMessage = ""
                        }
                        is RegisterInfoResult.Error -> {
                            registerInfoUiState.errorMessage = result.message
                        }
                        null -> {}
                    }
                } else if (event is RegisterInfoEvent.OnBack) {
                    currentView = View.Credentials
                }
            },
            accountType = registrationInfo.accountType,
        )
        View.Info2 -> registerInfoScreenCont(
            uiState = registerInfoUiState,
            onBack = { currentView = View.Info },
            onContinue = {
                registrationInfo.major = registerInfoUiState.major
                registrationInfo.tags = registerInfoUiState.tags
                currentView = View.Pfp
            }
        )
        View.Pfp -> {
            if (registrationInfo.accountType == UserType.STUDENT) {
                registerPfpUiState.prompt = "Please select a profile picture"
            } else {
                registerPfpUiState.prompt = "Please select a logo"
            }
            registerPfpScreen(
                uiState = registerPfpUiState,
                onContinue = {
                    registrationInfo.profilePicture = registerPfpUiState.profilePicture
                    onRegister()
                },
                onBack = { currentView = View.Info }
            )
        }
    }
}