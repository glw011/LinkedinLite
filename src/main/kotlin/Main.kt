
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.PictureDAO
import dao.UserDAO
import data.Organization
import data.Student
import data.User
import model.ModelManager
import model.Org
import model.UserType
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.login.LoginEvent
import ui.views.login.LoginResult
import ui.views.login.LoginUiState
import ui.views.login.loginScreen
import ui.views.login.onLoginEvent
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
    var accountType: UserType = UserType.STUDENT,
)

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentUser: User? by rememberSaveable{ mutableStateOf(null) }
    var currentView by rememberSaveable { mutableStateOf(View.Login) }
    val registrationInfo by rememberSaveable { mutableStateOf(RegistrationInfo()) }
    var loginUiState by rememberSaveable { mutableStateOf(LoginUiState()) }

    updateScreenDimensions()

    when (currentView) {
        View.Login -> {
            loginScreen(
                uiState = loginUiState,
                onEvent = { event ->
                    val result = onLoginEvent(event, loginUiState)
                    if (event is LoginEvent.OnLogin) {
                        when (result) {
                            is LoginResult.Success -> {
                                currentView = View.Home
                                val userId = ModelManager.getUserId(loginUiState.email)
                                val userType = ModelManager.getUserType(userId)
                                currentUser = when (userType) {
                                    UserType.STUDENT -> Student.login(loginUiState.email)
                                    UserType.ORG -> Organization.login(loginUiState.email)
                                    else -> null
                                }
                            }
                            is LoginResult.Error -> { loginUiState.errorMessage = result.message }
                            null -> {}
                        }
                    } else if (event is LoginEvent.OnRegister) {
                        currentView = View.Register
                        loginUiState = LoginUiState()
                    }
                }
            )
        }
        View.Register -> {
            Register(
                registrationInfo = registrationInfo,
                onBack = { currentView = View.Login },
                onRegister = {
                    currentView = View.Home
                    if (registrationInfo.accountType == UserType.STUDENT) {
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

                        val pfp = (currentUser as Student).getProfilePicture()
                        var imgId = 0

                        if (pfp.height != 0 && pfp.width != 0) {
                            imgId = PictureDAO.addNewImg(
                                imageBitmapToBufferedImage(pfp),
                                (currentUser as Student).getId()
                            )

                            UserDAO.setProfileImg(
                                (currentUser as Student).getId(),
                                imgId
                            )
                        }

                    } else {
                        currentUser = Organization.register(
                            registrationInfo.name,
                            registrationInfo.email,
                            registrationInfo.password,
                            registrationInfo.schoolName,
                            registrationInfo.profilePicture,
                            registrationInfo.tags,
                        )

                        val pfp = (currentUser as Student).getProfilePicture()
                        var imgId = 0

                        if (pfp.height != 0 && pfp.width != 0) {
                            imgId = PictureDAO.addNewImg(
                                imageBitmapToBufferedImage(pfp),
                                (currentUser as Organization).getId()
                            )

                            UserDAO.setProfileImg(
                                (currentUser as Organization).getId(),
                                imgId
                            )
                        }
                    }
                }
            )
        }
        View.Home -> {
            if (currentUser != null) {
                val profileUiState = ProfileUiState(currentUser!!)
                profileUiState.headerInfo.name = currentUser!!.getName()
                profileUiState.headerInfo.location = currentUser!!.getLocation()
                profileUiState.headerInfo.school = currentUser!!.getSchool()
                profileUiState.headerInfo.profilePicture = currentUser!!.getProfilePicture()
                profileUiState.headerInfo.title = currentUser!!.title
                profileUiState.tags = currentUser!!.getTags()
                UI(profileUiState, currentUser!!)
            } else {
                throw IllegalStateException("Failed to register user")
            }
        }
    }
}