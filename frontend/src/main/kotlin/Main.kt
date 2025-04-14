import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.theme.MainTheme
import ui.views.OrgProfileTab
import ui.views.UI
import ui.views.loginScreen
import ui.views.registerOrgInfoScreen
import ui.views.registerOrgPfpScreen
import ui.views.registerOrgScreen
import ui.views.registerSelectScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        MainTheme {
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
fun App() {
    MainTheme(){} // should update theme at the start of the app

    // Main function to run the application
    var currentView by rememberSaveable { mutableStateOf(View.Login) }

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
        val onContinue: () -> Unit = {
            // Handle continue logic here
            // For now, just switch to the registration profile picture view
            currentView = View.RegisterOrgPfp
        }
        val onBack: () -> Unit = {
            // Handle back logic here
            // For now, just switch to the organization main view
            currentView = View.RegisterOrgMain
        }
        registerOrgInfoScreen(
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.RegisterOrgPfp){
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