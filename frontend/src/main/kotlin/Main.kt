import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.ProfileViewModel
import ui.theme.MainTheme
import ui.views.UI
import ui.views.loginScreen
import ui.views.registerOrgInfoScreen
import ui.views.registerOrgPfpScreen
import ui.views.registerOrgScreen
import ui.views.registerSelectScreen
import utils.updateScreenDimensions
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

@Composable
fun App(profileViewModel: ProfileViewModel = ProfileViewModel()) {
    // Main function to run the application
    var currentView by rememberSaveable { mutableStateOf(View.Login) }

    updateScreenDimensions()

    // Observe the profileViewModel state
    val profileState by profileViewModel.uiState.collectAsState()

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
        registerOrgScreen(
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.RegisterOrgInfo) {
        var organizationName by rememberSaveable { mutableStateOf("") }
        var schoolName by rememberSaveable { mutableStateOf("") }
        var organizationTags by rememberSaveable { mutableStateOf(listOf<String>()) }
        val onContinue: () -> Unit = {
            // Handle continue logic here
            // For now, just switch to the registration profile picture view
            currentView = View.RegisterOrgPfp
            profileViewModel.setName(organizationName)
            profileViewModel.setSchool(schoolName)
        }
        val onBack: () -> Unit = {
            // Handle back logic here
            // For now, just switch to the organization main view
            currentView = View.RegisterOrgMain
        }
        registerOrgInfoScreen(
            onContinue = onContinue,
            onBack = onBack,
            onOrgNameChanged = { organizationName = it },
            onSchoolNameChanged = { schoolName = it },
            onOrgTagsChanged = {}
        )
    } else if (currentView == View.RegisterOrgPfp) {
        val onContinue: () -> Unit = {
            // Handle continue logic here
            // For now, just switch to the home view
            currentView = View.Home
        }
        val onBack: () -> Unit = {
            // Handle back logic here
            // For now, just switch to the registration info view
            currentView = View.RegisterOrgInfo
        }
        registerOrgPfpScreen(
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.Home) {
        // Show home screen
        UI()
    }
}