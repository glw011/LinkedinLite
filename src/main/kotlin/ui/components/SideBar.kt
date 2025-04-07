/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.res.LIGHT_PURPLE

var FadeSpeed by mutableStateOf(100)

/**
 * Indicates whether the search functionality is currently active.
 *
 * When `true`, the search bar should be displayed or some action should be taken,
 * when `false`, the search bar should be hidden.
 * This value will be updated based on user interaction.
 */
var searchActive by mutableStateOf(false)

/**
 * Composable function for the sidebar navigation.
 *
 * @param selectedTab The currently selected tab.
 * @param onTabSelected Lambda function to call when a tab is selected.
 */
@Composable
fun Sidebar(selectedTab: String, onTabSelected: (String) -> Unit) {
    Column(
        modifier = Modifier
            .width(64.dp)
            .fillMaxHeight()
            .background(Color.LightGray)
            .padding(2.dp)
    ) {
        // ------------------------------------------------------------------------------------ TABS
        searchTabButton(
            isSelected = searchActive,
            onClick = {
                searchActive = !searchActive
                println("Search Selected")
            }
        )

        homeTabButton(
            isSelected = selectedTab == "Home",
            onClick = {
                searchActive = false
                onTabSelected("Home")
                println("Home Tab")
            }
        )

        peopleOrgsTabButton(
            isSelected = selectedTab == "People / Orgs",
            onClick = {
                onTabSelected("People / Orgs")
                println("People / Orgs Tab")
            }
        )

        postTabButton(
            isSelected = selectedTab == "Post",
            onClick = {
                searchActive = false
                onTabSelected("Post")
                println("Post Tab")
            }
        )

        settingsTabButton(
            isSelected = selectedTab == "Settings",
            onClick = {
                searchActive = false
                onTabSelected("Settings")
                println("Settings Tab")
            }
        )
        // -----------------------------------------------------------------------------------------
    }
}

/**
 * Composable function for the "People / Orgs" tab button in the sidebar.
 *
 * @param isSelected True if this tab is currently selected, false otherwise.
 * @param onClick Lambda function to call when the tab is clicked.
 */
@Composable
fun peopleOrgsTabButton(isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .size(64.dp)
            .width(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        // Person Icon
        Icon(
            imageVector = Icons.Filled.Person,
            contentDescription = "People / Orgs",
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 4.dp)
        )

        // Selection Indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = FadeSpeed)),
            exit = fadeOut(animationSpec = tween(durationMillis = FadeSpeed))
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 48.dp, bottom = 4.dp)
                    .size(width = 4.dp, height = 32.dp)
                    .background(LIGHT_PURPLE, shape = RoundedCornerShape(32.dp))
            )
        }
    }
}

/**
 * Composable function for the "Home" tab button in the sidebar.
 *
 * @param isSelected True if this tab is currently selected, false otherwise.
 * @param onClick Lambda function to call when the tab is clicked.
 */
@Composable
fun homeTabButton(isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    // Box to contain icon
    Box(
        modifier = Modifier
            .size(64.dp)
            .width(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        // Home Icon
        Icon(
            imageVector = Icons.Filled.Home,
            contentDescription = "Home",
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 4.dp)
        )

        // Selection Indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = FadeSpeed)),
            exit = fadeOut(animationSpec = tween(durationMillis = FadeSpeed))
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 48.dp, bottom = 4.dp)
                    .size(width = 4.dp, height = 32.dp)
                    .background(LIGHT_PURPLE, shape = RoundedCornerShape(32.dp))
            )
        }
    }
}

/**
 * Composable function for the "Search" tab button in the sidebar.
 *
 * @param isSelected True if this tab is currently selected, false otherwise.
 * @param onClick Lambda function to call when the tab is clicked.
 */
@Composable
fun searchTabButton(isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    // Box to contain icon
    Box(
        modifier = Modifier
            .size(64.dp)
            .width(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        // Search Icon
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = "Search",
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 4.dp)
        )

        // Selection Indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = FadeSpeed)),
            exit = fadeOut(animationSpec = tween(durationMillis = FadeSpeed))
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 48.dp, bottom = 4.dp)
                    .size(width = 6.dp, height = 6.dp)
                    .background(LIGHT_PURPLE, shape = RoundedCornerShape(32.dp))
            )
        }
    }
}

@Composable
fun postTabButton(isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    // Box to contain icon
    Box(
        modifier = Modifier
            .size(64.dp)
            .width(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {

        // Add Icon
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Home",
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 4.dp)
        )

        // Selection Indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = FadeSpeed)),
            exit = fadeOut(animationSpec = tween(durationMillis = FadeSpeed))
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 48.dp, bottom = 4.dp)
                    .size(width = 4.dp, height = 32.dp)
                    .background(LIGHT_PURPLE, shape = RoundedCornerShape(32.dp))
            )
        }
    }
}

@Composable
fun settingsTabButton(isSelected: Boolean, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    // Box to contain icon
    Box(
        modifier = Modifier
            .size(64.dp)
            .width(64.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        // Settings Icon
        Icon(
            imageVector = Icons.Filled.Settings,
            contentDescription = "Settings",
            modifier = Modifier
                .size(32.dp)
                .padding(bottom = 4.dp)
        )
        // Selection Indicator
        AnimatedVisibility(
            visible = isSelected,
            enter = fadeIn(animationSpec = tween(durationMillis = FadeSpeed)),
            exit = fadeOut(animationSpec = tween(durationMillis = FadeSpeed))
        ) {

            // Selection Indicator
            Box(
                modifier = Modifier
                    .padding(start = 48.dp, bottom = 4.dp)
                    .size(width = 4.dp, height = 32.dp)
                    .background(LIGHT_PURPLE, shape = RoundedCornerShape(32.dp))
            )
        }
    }
}