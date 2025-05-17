
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import data.AccountType
import data.User
import model.ModelManager
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.loginScreen
import ui.views.register.RegisterInfoUiState
import ui.views.register.RegisterOrgInfoUiState
import ui.views.register.RegisterPfpUIState
import ui.views.register.registerCredentialsScreen
import ui.views.register.registerInfoScreen
import ui.views.register.registerOrgInfoScreen
import ui.views.register.registerPfpScreen
import ui.views.register.registerSelectScreen
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

    var accountType: AccountType by rememberSaveable { mutableStateOf(AccountType.STUDENT) }

    var currentUser: User? by rememberSaveable{ mutableStateOf(null) }

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
//            current_user.accountType = AccountType.INDIVIDUAL
//            profileUiState.headerInfo.title = "Student"
            currentView = View.RegisterMain
        }
        val onOrganization: () -> Unit = {
            // Handle organization registration logic here
//            current_user.accountType = AccountType.ORGANIZATION
//            profileUiState.headerInfo.title = "Organization"
            accountType = AccountType.ORGANIZATION
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
                if (accountType == AccountType.STUDENT) {
                    currentView = View.RegisterInfo
                } else {
                    currentView = View.RegisterOrgInfo
                }
            },
            onBack = { currentView = View.RegisterSelect },
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

            if (accountType == AccountType.STUDENT) {
                currentUser = User.register(
                    registerInfoUiState.name + " " + registerInfoUiState.surname,
                    "example@thing.com",
                    registerInfoUiState.schoolName,
                    accountType
                )

                profileUiState.headerInfo.title = "Student"
                profileUiState.tags = registerInfoUiState.tags
                profileUiState.headerInfo.profilePicture.value = registerPfpUiState.profilePicture.value
            } else {
                currentUser = User.register(
                    registerOrgInfoUiState.orgName,
                    "example@org.com",
                    registerOrgInfoUiState.schoolName,
                    accountType
                )

                profileUiState.headerInfo.title = "Organization"
                profileUiState.tags = registerOrgInfoUiState.orgTags
                profileUiState.headerInfo.profilePicture.value = registerPfpUiState.profilePicture.value
            }
            profileUiState.headerInfo.name = currentUser!!.getName()
            profileUiState.headerInfo.school = currentUser!!.getSchool()
        }
        val onBack: () -> Unit = {
            if (accountType == AccountType.STUDENT) {
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
        currentUser?.let { UI(profileUiState, it) }
    }
}