/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import ui.components.styles.styledDropDownList
import ui.components.styles.styledTextField
import ui.views.home.SEARCH_BAR_TEXT

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
fun searchBar(
    onSearchTextChanged: (String) -> Unit,
    hasFilter: Boolean = false,
    dropdownItems: List<String> = listOf(""),
    onFilterSelectionChanged: (List<String>) -> Unit = {}, // ← new
    alwaysShow: Boolean = false,
) {
    var searchText by remember { mutableStateOf("") }
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeight = remember(windowInfo) {
        with(density) { windowInfo.containerSize.height }
    }
    val selectedFilters = remember { mutableStateListOf<String>() }

    if (searchActive || alwaysShow)
        SEARCH_BAR_TEXT = searchText
    else {
        searchText = ""
        SEARCH_BAR_TEXT = ""
    }

//    if (searchActive)
//        Spacer(modifier = Modifier.padding(top = 16.dp))

    AnimatedVisibility(
        visible = searchActive || alwaysShow,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 250),
            initialOffsetY = { -screenHeight }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 250),
            targetOffsetY = { -screenHeight }
        )
    ) {
        Row {
            Row (modifier = Modifier.weight(1f)){
                // Search Bar
                styledTextField(
                    onTextChanged = {
                        searchText = it
                        onSearchTextChanged(it)
                    },
                    unfocusedText = "Search for People / Organizations",
                    width = 0,   // Arbitrarily large number for now, will change later
                    icon = Icons.Filled.Search,
                    showIcon = true,
                    iconAlignment = Alignment.CenterStart,
                    fillMaxWidth = true
                )
            }

            Spacer(modifier = Modifier.padding(start = 8.dp))

            Row {
                if (hasFilter) {
                    styledDropDownList(
                        items = dropdownItems,
                        xAlignment = Alignment.End,
                        multiSelect = true,
                        width = 256,
                        noSelectionText = "Filters",
                        onSelectionChange = { onFilterSelectionChanged(it) }
                    )
                }
            }
        }

        if (searchActive || alwaysShow)
            Spacer(modifier = Modifier.padding(top = 16.dp))
    }
}