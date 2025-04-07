import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.views.UI
import ui.views.loginScreen
import ui.views.registerOrgScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        PreviewApp()
    }
}

enum class View {
    Login,
    Home,
    RegisterOrgMain,
    RegisterOrgInfo,
    RegisterOrgPfp,
}

@Composable
fun App() {
    // Main function to run the application
    var searchActive by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }
    var selectedTab by rememberSaveable { mutableStateOf("Login") }
    var currentView by rememberSaveable { mutableStateOf(View.Login) }

    if (currentView == View.Login) {
        // Show login screen
        loginScreen(
            onLogin = { currentView = View.Home },
            onRegister = { currentView = View.RegisterOrgMain }
        )
    } else if (currentView == View.RegisterOrgMain) {
        // Show registration screen
        registerOrgScreen(
            onContinue = { currentView = View.RegisterOrgInfo },
            onBack = { currentView = View.Login }
        )
    } else if (currentView == View.RegisterOrgInfo) {
    } else {
        // Show main UI
        UI()
    }
}

@Composable
@Preview
fun PreviewApp() {
    // Preview function for the application
    App()
}