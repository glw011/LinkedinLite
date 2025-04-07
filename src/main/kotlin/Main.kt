import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.views.UI

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        PreviewApp()
    }
}

@Composable
fun App() {
    // Main function to run the application
//    var searchActive by rememberSaveable { mutableStateOf(false) }
//    var searchText by rememberSaveable { mutableStateOf("") }
//    var selectedTab by rememberSaveable { mutableStateOf("Login") }
//    var currentView by rememberSaveable { mutableStateOf("Login") }
    UI()
}

@Composable
@Preview
fun PreviewApp() {
    // Preview function for the application
    App()
}