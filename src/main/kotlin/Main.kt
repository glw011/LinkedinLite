import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.AccountType
import data.User
import data.current_user
import ui.components.profilecard.getRecommendedUsers
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.loginScreen
import ui.views.register.*
import util.updateScreenDimensions
import java.awt.Dimension

val testUsers = listOf(
    User(
        name = "John",
        surname = "Doe",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Harvard University",
        tags = mutableListOf("Philosophy", "Reading"),
        profilePicture = null,
    ),
    User(
        name = "Jane",
        surname = "Smith",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Stanford University",
        tags = mutableListOf("Computer Science", "Artificial Intelligence"),
        profilePicture = null,
    ),
    User(
        name = "Boltdog Robotics",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Physics", "Robotics"),
        profilePicture = null,
    ),
    User(
        name = "Tech Innovators",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Stanford University",
        tags = mutableListOf("Biology", "Genetics"),
        profilePicture = null,
    ),
    User(
        name = "Alice",
        surname = "Johnson",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "MIT",
        tags = mutableListOf("Physics", "Robotics"),
        profilePicture = null,
    ),
    User(
        name = "Bob",
        surname = "Brown",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Stanford University",
        tags = mutableListOf("Biology", "Genetics"),
        profilePicture = null,
    ),
    User(
        name = "Charlie",
        surname = "Davis",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Harvard University",
        tags = mutableListOf("Philosophy", "Mathematics"),
        profilePicture = null,
    ),
    User(
        name = "Delta",
        surname = "Smith",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Computer Science", "Artificial Intelligence"),
        profilePicture = null,
    ),
    User(
        name = "Eve",
        surname = "Williams",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "MIT",
        tags = mutableListOf("Physics", "Robotics"),
        profilePicture = null,
    ),
    User(
        name = "Frank",
        surname = "Jones",
        accountType = AccountType.INDIVIDUAL,
        schoolName = "Stanford University",
        tags = mutableListOf("Biology", "Genetics"),
        profilePicture = null,
    ),
    User(
        name = "Association of Computing Machinery",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Computer Science", "Artificial Intelligence"),
        profilePicture = null,
    ),
    User(
        name = "Society of Physics Students",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Physics", "Robotics"),
        profilePicture = null,
    ),
    User(
        name = "Biology Club",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Biology", "Genetics"),
        profilePicture = null,
    ),
    User(
        name = "Philosophy Society",
        accountType = AccountType.ORGANIZATION,
        schoolName = "Louisiana Tech University",
        tags = mutableListOf("Philosophy", "Mathematics"),
        profilePicture = null,
    ),
)

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "LinkedInLite") {
        window.minimumSize = Dimension(800, 600)
        MainTheme {
            App() // or pass sdao into App if needed
        }
    }
}


enum class View {
    Login,
    Home,
    RegisterSelect,
    RegisterMain,
    RegisterInfo,
    RegisterPfp,
    RegisterOrgInfo,
}

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentView by rememberSaveable { mutableStateOf(View.Login) }

    val profileUiState by rememberSaveable { mutableStateOf(ProfileUiState()) }

    var registerPfpUiState by rememberSaveable { mutableStateOf(RegisterPfpUIState()) }
    var registerInfoUiState by rememberSaveable { mutableStateOf(RegisterInfoUiState()) }
    var registerOrgInfoUiState by rememberSaveable { mutableStateOf(RegisterOrgInfoUiState()) }

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
            current_user.accountType = AccountType.INDIVIDUAL
            profileUiState.headerInfo.title = "Student"
            currentView = View.RegisterMain
        }
        val onOrganization: () -> Unit = {
            // Handle organization registration logic here
            current_user.accountType = AccountType.ORGANIZATION
            profileUiState.headerInfo.title = "Organization"
            currentView = View.RegisterMain
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
    } else if (currentView == View.RegisterMain) {
        registerCredentialsScreen(
            onContinue = {
                // Handle continue logic here
                // For now, just switch to the registration info view
                if (current_user.accountType == AccountType.INDIVIDUAL) {
                    currentView = View.RegisterInfo
                } else {
                    currentView = View.RegisterOrgInfo
                }
            },
            onBack = {
                // Handle back logic here
                // For now, just switch to the registration select view
                currentView = View.RegisterSelect
            },
        )
    } else if (currentView == View.RegisterInfo) {
        registerInfoScreen(
            uiState = registerInfoUiState,
            onContinue = {
                // Handle continue logic here
                // For now, just switch to the home view
                currentView = View.RegisterPfp
            },
            onBack = {
                // Handle back logic here
                // For now, just switch to the registration main view
                registerInfoUiState = RegisterInfoUiState()
                registerPfpUiState = RegisterPfpUIState()
                currentView = View.RegisterMain
            },
        )
    } else if (currentView == View.RegisterOrgInfo) {
        // TODO: Add input validation
        val onContinue: () -> Unit = {
            currentView = View.RegisterPfp
        }
        val onBack: () -> Unit = {
            currentView = View.RegisterMain
            registerOrgInfoUiState = RegisterOrgInfoUiState()
        }
        registerOrgInfoScreen(
            uiState = registerOrgInfoUiState,
            onContinue = onContinue,
            onBack = onBack,
        )
    } else if (currentView == View.RegisterPfp) {
        val onContinue: () -> Unit = {
            currentView = View.Home

            current_user.profilePicture = registerPfpUiState.profilePicture.value
            profileUiState.headerInfo.profilePicture.value = current_user.profilePicture

            if (current_user.accountType == AccountType.INDIVIDUAL) {
                current_user.name = registerInfoUiState.name
                current_user.surname = registerInfoUiState.surname
                current_user.schoolName = registerInfoUiState.schoolName
                current_user.tags = registerInfoUiState.tags

                profileUiState.headerInfo.name =
                    current_user.name + " " + current_user.surname
            } else {
                current_user.name = registerOrgInfoUiState.orgName
                current_user.schoolName = registerOrgInfoUiState.schoolName
                current_user.tags = registerOrgInfoUiState.orgTags

                profileUiState.headerInfo.name = registerOrgInfoUiState.orgName
            }

            profileUiState.headerInfo.school = current_user.schoolName
            profileUiState.tags = current_user.tags
        }
        val onBack: () -> Unit = {
            if (current_user.accountType == AccountType.INDIVIDUAL) {
                currentView = View.RegisterInfo
            } else {
                currentView = View.RegisterOrgInfo
            }
        }
        registerPfpScreen(
            uiState = registerPfpUiState,
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.Home) {
        // Show home screen
        getRecommendedUsers(testUsers, AccountType.INDIVIDUAL)
        UI(profileUiState, current_user)
    }
}