import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.UI

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedIn for Students") {
        UI()
    }
}