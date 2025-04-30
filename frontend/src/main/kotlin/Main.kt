import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.loginScreen
import ui.views.registerorg.RegisterOrgInfoUiState
import ui.views.registerorg.RegisterOrgPfpUiState
import ui.views.registerorg.registerOrgInfoScreen
import ui.views.registerorg.registerOrgPfpScreen
import ui.views.registerorg.registerOrgScreen
import ui.views.registerorg.registerSelectScreen
import util.updateScreenDimensions
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        window.minimumSize = Dimension(800, 600)
        MainTheme() {
            App()
        }
    }
}

enum class View {
    Login,
    Home,
    RegisterSelect,
    RegisterOrgMain,
    RegisterOrgInfo,
    RegisterOrgPfp,
}

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentView by rememberSaveable { mutableStateOf(View.Login) }
    val profileUiState by rememberSaveable { mutableStateOf(ProfileUiState()) }
    var registerOrgInfoUiState by rememberSaveable { mutableStateOf(RegisterOrgInfoUiState()) }
    var registerOrgPfpUiState by rememberSaveable { mutableStateOf(RegisterOrgPfpUiState()) }

    updateScreenDimensions()

    if (currentView == View.Login) {
        // Show login screen
        val onLogin: () -> Unit = {
            // Handle login logic here
            // For now, just switch to the home view
            currentView = View.Home
        }
        val onRegister: () -> Unit = {
            // Handle registration logic here
            // For now, just switch to the registration view
            currentView = View.RegisterSelect
        }
        loginScreen(
            onLogin = onLogin,
            onRegister = onRegister
        )
    } else if (currentView == View.RegisterSelect) {
        val onPerson: () -> Unit = {
            // Handle person registration logic here
        }
        val onOrganization: () -> Unit = {
            // Handle organization registration logic here
            // For now, just switch to the organization main view
            currentView = View.RegisterOrgMain
        }
        val onBack: () -> Unit = {
            // Handle back logic here
            // For now, just switch to the login view
            currentView = View.Login
        }
        registerSelectScreen(
            onPerson = onPerson,
            onOrg = onOrganization,
            onBack = onBack
        )
    } else if (currentView == View.RegisterOrgMain) {
        val onContinue: () -> Unit = {
            // Handle continue logic here
            // For now, just switch to the registration info view
            currentView = View.RegisterOrgInfo
        }
        val onBack: () -> Unit = {
            // Handle back logic here
            // For now, just switch to the registration select view
            currentView = View.RegisterSelect
        }
        val onEmailChanged: () -> Unit = {
            // Handle email change logic here
        }
        val onPasswordChanged: () -> Unit = {
            // Handle password change logic here
        }
        registerOrgScreen(
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.RegisterOrgInfo) {
        // TODO: Add input validation
        val onContinue: () -> Unit = {
            currentView = View.RegisterOrgPfp
        }
        val onBack: () -> Unit = {
            currentView = View.RegisterOrgMain
            registerOrgInfoUiState = RegisterOrgInfoUiState()
            registerOrgPfpUiState = RegisterOrgPfpUiState()
        }
        registerOrgInfoScreen(
            uiState = registerOrgInfoUiState,
            onContinue = onContinue,
            onBack = onBack,
        )
    } else if (currentView == View.RegisterOrgPfp) {
        val onContinue: () -> Unit = {
            currentView = View.Home
            profileUiState.headerInfo.name = registerOrgInfoUiState.orgName
            profileUiState.headerInfo.school = registerOrgInfoUiState.schoolName
            profileUiState.tags = registerOrgInfoUiState.orgTags
            profileUiState.headerInfo.profilePicture.value = registerOrgPfpUiState.profilePicture.value
        }
        val onBack: () -> Unit = {
            currentView = View.RegisterOrgInfo
        }
        registerOrgPfpScreen(
            uiState = registerOrgPfpUiState,
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.Home) {
        // Show home screen
        UI(profileUiState)
    }
}