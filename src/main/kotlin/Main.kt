import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dao.StudentDAO
import data.AccountType
import data.User
import ui.theme.MainTheme
import ui.views.home.ProfileUiState
import ui.views.home.UI
import ui.views.loginScreen
import ui.views.register.RegisterInfoUiState
import ui.views.register.RegisterPfpUIState
import ui.views.register.registerCredentialsScreen
import ui.views.register.registerInfoScreen
import ui.views.registerorg.*
import util.updateScreenDimensions
import java.awt.Dimension
import dao.*
import model.*
import service.*
import util.*

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
    RegisterOrgMain,
    RegisterOrgInfo,
    RegisterOrgPfp,
}

/**
 * Main application composable.
 */
@Composable
fun App() {
    var currentUser: User? by rememberSaveable { mutableStateOf(User()) }
    var currentView by rememberSaveable { mutableStateOf(View.Login) }
    val profileUiState by rememberSaveable { mutableStateOf(ProfileUiState()) }
    var registerPfpUiState by rememberSaveable { mutableStateOf(RegisterPfpUIState()) }
    var registerInfoUiState by rememberSaveable { mutableStateOf(RegisterInfoUiState()) }
    var registerOrgInfoUiState by rememberSaveable { mutableStateOf(RegisterOrgInfoUiState()) }
    var registerOrgPfpUiState by rememberSaveable { mutableStateOf(RegisterOrgPfpUiState()) }

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
            currentUser!!.accountType = AccountType.INDIVIDUAL
            profileUiState.headerInfo.title = "Student"
            currentView = View.RegisterMain
        }
        val onOrganization: () -> Unit = {
            // Handle organization registration logic here
            // For now, just switch to the organization main view
            currentUser!!.accountType = AccountType.ORGANIZATION
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
                if (currentUser!!.accountType == AccountType.INDIVIDUAL) {
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
                currentView = View.RegisterOrgPfp
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
            currentView = View.RegisterOrgPfp
        }
        val onBack: () -> Unit = {
            currentView = View.RegisterMain
            registerOrgInfoUiState = RegisterOrgInfoUiState()
            registerOrgPfpUiState = RegisterOrgPfpUiState()
        }
        registerOrgInfoScreen(
            uiState = registerOrgInfoUiState,
            onContinue = onContinue,
            onBack = onBack,
        )
    } else if (currentView == View.RegisterOrgPfp) {
        val onContinue: () -> Unit = {
            currentView = View.Home
            if (currentUser!!.accountType == AccountType.INDIVIDUAL) {
                profileUiState.headerInfo.name = registerInfoUiState.name + " " + registerInfoUiState.surname
                profileUiState.headerInfo.school = registerInfoUiState.schoolName
                profileUiState.tags = registerInfoUiState.tags
                profileUiState.headerInfo.profilePicture.value = registerOrgPfpUiState.profilePicture.value
            } else {
                profileUiState.headerInfo.name = registerOrgInfoUiState.orgName
                profileUiState.headerInfo.school = registerOrgInfoUiState.schoolName
                profileUiState.tags = registerOrgInfoUiState.orgTags
                profileUiState.headerInfo.profilePicture.value = registerOrgPfpUiState.profilePicture.value
            }
        }
        val onBack: () -> Unit = {
            if (currentUser!!.accountType == AccountType.INDIVIDUAL) {
                currentView = View.RegisterInfo
            } else {
                currentView = View.RegisterOrgInfo
            }
        }
        registerOrgPfpScreen(
            uiState = registerOrgPfpUiState,
            onContinue = onContinue,
            onBack = onBack
        )
    } else if (currentView == View.Home) {
        // Show home screen
        UI(profileUiState, currentUser!!)
    }
}