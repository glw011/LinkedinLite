
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.AccountType
import data.Organization
import data.Student
import data.User
import model.ModelManager
import ui.components.profilecard.getRecommendedUsers
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.loginScreen
import ui.views.register.Register
import util.updateScreenDimensions
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        ModelManager.initModelManager()
        window.minimumSize = Dimension(800, 600)
        MainTheme {
            App() // or pass sdao into App if needed
        }
    }
}


enum class View {
    Login,
    Home,
    Register,
}

data class RegistrationInfo(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var schoolName: String = "",
    var tags: List<String> = emptyList(),
    var profilePicture: ImageBitmap = ImageBitmap(0, 0),
    var major: String = "",
    var accountType: AccountType = AccountType.STUDENT,
)

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentView by rememberSaveable { mutableStateOf(View.Login) }
    val registrationInfo by rememberSaveable { mutableStateOf(RegistrationInfo()) }
    val profileUiState by rememberSaveable { mutableStateOf(ProfileUiState()) }

    var currentUser: User? by rememberSaveable{ mutableStateOf(null) }

    updateScreenDimensions()

    if (currentView == View.Login) {
        // Show login screen
        val onLogin: () -> Unit = {
            currentView = View.Home
        }
        val onRegister: () -> Unit = {
            // Handle registration logic here
            // For now, just switch to the registration view
            currentView = View.Register
        }
        loginScreen(
            onLogin = onLogin,
            onRegister = onRegister
        )
    } else if (currentView == View.Register) {
        Register(
            registrationInfo = registrationInfo,
            onBack = { currentView = View.Login },
            onRegister = {
                currentView = View.Home
                if (registrationInfo.accountType == AccountType.STUDENT) {
                    currentUser = Student.register(
                        registrationInfo.name,
                        registrationInfo.surname,
                        registrationInfo.email,
                        registrationInfo.password,
                        registrationInfo.schoolName,
                        registrationInfo.major,
                        registrationInfo.profilePicture,
                        registrationInfo.tags,
                    )
                } else {
                    currentUser = Organization.register(
                        registrationInfo.name,
                        registrationInfo.email,
                        registrationInfo.password,
                        registrationInfo.schoolName,
                        registrationInfo.profilePicture,
                        registrationInfo.tags,
                    )
                }
            }
        )
    } else if (currentView == View.Home) {
        // Show home screen
        if (currentUser != null) {
            profileUiState.headerInfo.name = currentUser!!.getName()
            profileUiState.headerInfo.location = currentUser!!.getLocation()
            profileUiState.headerInfo.school = currentUser!!.getSchool()
            profileUiState.headerInfo.profilePicture = currentUser!!.getProfilePicture()
            UI(profileUiState, currentUser!!)
        } else {
            throw IllegalStateException("Failed to register user")
        }
    }
}