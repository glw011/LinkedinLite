/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Main UI file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.Sidebar
import ui.components.searchActive
import ui.theme.DARK_MODE
import ui.theme.MainTheme

/**
 * The main UI composable for the LinkedInLite application.
 *
 * This function sets up the primary layout structure, including the sidebar
 * and the main content area. It manages the currently selected tab and
 * delegates the rendering of content to other composable functions.
 */
@Composable
fun UI() {
    Surface(modifier = Modifier.fillMaxSize(), color = if (DARK_MODE) ui.theme.backgroundDark else ui.theme.backgroundLight) {
        var selectedTab by remember { mutableStateOf("Home") }

        Row(Modifier.fillMaxSize()) {

            // Always Show Sidebar
            Sidebar(
                selectedTab = selectedTab,
                onTabSelected = { tabName -> selectedTab = tabName }
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
                    "People / Orgs" -> peopleOrgsTabContent(onSearchTextChanged = { println(it) })
                    // Add your new tab content composables here
                }
            }

            Spacer(modifier = Modifier.padding(end = 16.dp))
        }
    }
}