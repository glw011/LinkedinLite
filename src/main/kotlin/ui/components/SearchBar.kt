/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo

/**
 * Composable function for the search bar UI element.
 *
 * This function renders a search bar that slides in from the top of the screen when
 * activated and slides out when deactivated. It includes text input, placeholder text,
 * a search icon, and handles focus and user input.
 *
 * @param onSearchTextChanged Lambda function to call when the search text changes.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchBar(onSearchTextChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeight = remember(windowInfo) {
        with(density) { windowInfo.containerSize.height }
    }

    AnimatedVisibility(
        visible = searchActive,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 250),
            initialOffsetY = { -screenHeight }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 250),
            targetOffsetY = { -screenHeight }
        )
    ) {
        // Search Bar
        styledTextField(
            onTextChanged = { searchText = it },
            unfocusedText = "Search for People / Organizations",
            width = 5000,
            icon = Icons.Filled.Search,
        )
    }
}