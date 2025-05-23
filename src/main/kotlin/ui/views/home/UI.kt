/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Main UI file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.views.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.User
import model.UserType
import ui.components.Sidebar
import ui.components.searchActive

/**
 * The main UI composable for the LinkedInLite application.
 *
 * This function sets up the primary layout structure, including the sidebar
 * and the main content area. It manages the currently selected tab and
 * delegates the rendering of content to other composable functions.
 *
 * @param profileUiState The UI state of the profile, containing header information and other data.
 * @param currentUser The current user object, containing user information and account type.
 */
@Composable
fun UI(
    profileUiState: ProfileUiState,
    currentUser: User,
    onLogout: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        var selectedTab by remember { mutableStateOf("Home") }

        Row(Modifier.fillMaxSize()) {

            // Always Show Sidebar
            Sidebar(
                userProfilePicture = profileUiState.headerInfo.profilePicture,
                selectedTab = selectedTab,
                onTabSelected = { tabName -> selectedTab = tabName },
                currentUser
            )

            // Switch to People / Orgs Tab if Search Bar is Active
            if (selectedTab != "People / Orgs" && searchActive)
                selectedTab = "People / Orgs"

            // Disable Search Bar if not on People / Orgs Tab (Might change later)
            if (selectedTab != "People / Orgs")
                searchActive = false

            Spacer(modifier = Modifier.padding(start = 16.dp))

            // Show Tab Content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {

                // Switch Tabs
                when (selectedTab) {
                    "People / Orgs" -> peopleOrgsTabContent()
                    "My Profile" ->
                        if (currentUser.getAccountType() == UserType.ORG) {
                            OrgProfileTab(currentUser, profileUiState)
                        } else {
                            IndividualProfileTab(currentUser, profileUiState)
                        }
                    "Home" -> homeTab()
                    "Post" -> postTab(currentUser)
                    "Notifications" -> NotificationsTab(currentUser, NotificationsUiState(currentUser), profileUiState)
                    "Settings" -> SettingsTab(onLogout = onLogout)
                    // Add your new tab content composables here
                }
            }

            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
    }
}