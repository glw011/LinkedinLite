/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import ui.components.dropdownList
import ui.components.searchBar

/**
 * Stores the text currently entered in the search bar.
 *
 * This variable is global so that it can be accessed from other parts of the UI.
 * It is updated whenever the text in the search bar changes.
 */
// Global because it needs to be accessed by UI()
var SEARCH_BAR_TEXT by mutableStateOf("")

/**
 * Composable function for the People / Organizations tab content.
 *
 * This function displays a search bar when search is active and handles user input.
 *
 * @param onSearchTextChanged Lambda function to call when the search text changes.
 */
@Composable
fun peopleOrgsTabContent(onSearchTextChanged: (String) -> Unit) {
    val dropdownItems = remember {
        listOf("TEST1", "TEST2")
    }
    // This tab has a search bar
    searchBar(){ onSearchTextChanged(it) }
}