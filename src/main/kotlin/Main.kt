
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.PictureDAO
import dao.UserDAO
import data.Organization
import data.Student
import data.User
import model.ModelManager
import model.UserType
import service.UserService
import ui.components.profilecard.Associate
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.login.LoginEvent
import ui.views.login.LoginResult
import ui.views.login.LoginUiState
import ui.views.login.loginScreen
import ui.views.login.onLoginEvent
import ui.views.register.Register
import util.readLinesFromFile
import util.writeToFile
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        ModelManager.initModelManager()
        window.minimumSize = Dimension(800, 600)
        MainTheme {
            App()
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

var global_current_user: User? = null

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentUser: User? by rememberSaveable{ mutableStateOf(null) }
    var currentView by rememberSaveable { mutableStateOf(View.Login) }
    val registrationInfo by rememberSaveable { mutableStateOf(RegistrationInfo()) }
    var loginUiState by rememberSaveable { mutableStateOf(LoginUiState()) }

    try {
        if (currentUser == null) {
            val email = readLinesFromFile()[0]
            val userId = ModelManager.getUserId(email)
            val userType = ModelManager.getUserType(userId)
            currentUser = when (userType) {
                UserType.STUDENT -> Student.login(email)
                UserType.ORG -> Organization.login(email)
                else -> null
            }
            if (currentUser != null) {
                currentView = View.Home
            }
        }
    } catch (e: IndexOutOfBoundsException) {
        // No previous login data found, continue to login screen
    } catch (e: NullPointerException) {
        // Previous login data found but user is null, continue to login screen
    }

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
                                val email = currentUser!!.getEmail()
                                writeToFile("${email}\n${UserService().getHashedPassword(email)}")
                                loginUiState = LoginUiState()
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
                    val email = currentUser!!.getEmail()
                    writeToFile("${email}\n${UserService().getHashedPassword(email)}")
                }
            )
        }
        View.Home -> {
            if (currentUser != null) {
                var profileUiState = ProfileUiState(currentUser!!)
                profileUiState.tags = currentUser!!.getTags()
                profileUiState.recommendedPeople = currentUser!!.getRecommendedStudents()
                profileUiState.relatedOrganizations = currentUser!!.getRelatedOrganizations()
                val associates = mutableListOf<Associate>()
                if (currentUser is Organization) {
                    for (member in (currentUser as Organization).getMembers()) {
                        associates.add(
                            Associate(
                                member.getEmail(),
                                member.getName(),
                                (currentUser as Organization).getMemberRole(member),
                                member.getProfilePicture()
                            )
                        )
                    }
                } else {
                    for (organization in (currentUser as Student).getOrganizations()) {
                        associates.add(
                            Associate(
                                organization.getEmail(),
                                organization.getName(),
                                organization.getMemberRole(currentUser as Student),
                                organization.getProfilePicture()
                            )
                        )
                    }
                }
                profileUiState.associates = associates.toMutableStateList()
                UI(
                    profileUiState = profileUiState,
                    currentUser = currentUser!!,
                    onLogout = {
                        currentUser!!.logout()
                        currentUser = null
                        currentView = View.Login
                    },
                )
            } else {
                throw IllegalStateException("Failed to register user")
            }
        }
    }

    global_current_user = currentUser
}