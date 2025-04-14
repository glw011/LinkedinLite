/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import data.DataSource.searchFilters
import ui.components.profilePreview
import ui.components.searchActive
import ui.components.searchBar

/**
 * Stores the text currently entered in the search bar.
 *
 * This variable is global so that it can be accessed from other parts of the UI.
 * It is updated whenever the text in the search bar changes.
 */
var SEARCH_BAR_TEXT by mutableStateOf("")

/**
 *  List of filters (tags) currently applied to the search.
 */
var SEARCH_FILTERS by mutableStateOf(listOf<String>())

/**
 * Data class representing a profile with a name, bio, and profile picture.
 *
 * @property pfp The profile picture as an ImageBitmap (can be null).
 * @property name The name associated with the profile.
 * @property bio A short biography or description for the profile.
 */
data class ProfileData(
    val pfp: ImageBitmap?,
    val name: String,
    val bio: String,
    val tags: List<String> = listOf()
)

/**
 * TEMPORARY: REMOVE WHEN WE HAVE REAL DATA
 * A list of dummy profile data for testing and demonstration purposes.
 */
val dummyProfileList = mutableStateListOf<ProfileData>().apply {
    repeat(30) { i ->
        val name = "User ${i + 1}"
        val bio = "Made up bio for user ${i + 1}, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."
        add(ProfileData(null, name, bio))
    }

    repeat( 5 ) { i ->
        val name = "Organization ${i + 1}"
        val bio = "Made up bio for organization ${i + 1}, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."
        add(ProfileData(null, name, bio))
    }
    add(ProfileData(null, "Harrison Day", "Billionaire, philanthropist, and entrepreneur. Co-founder of linkedin lite. Current Occupation: gettin' that money"))
    add(ProfileData(null, "LATech AI", "Made up bio for organization LATech AI, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."))
}.shuffled()

/**
 * Composable function for the People / Organizations tab content.
 *
 * This function displays a search bar when search is active and handles user input.
 *
 * @param onSearchTextChanged Lambda function to call when the search text changes.
 */
@Composable
fun peopleOrgsTabContent(onSearchTextChanged: (String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(), // Make the Column fill the width
        horizontalAlignment = Alignment.CenterHorizontally // Center everything horizontally
    ) {
        if (searchActive)
            Spacer(modifier = Modifier.padding(top = 16.dp))

        // This tab has a search bar
        searchBar(
            onSearchTextChanged = {
                SEARCH_BAR_TEXT = it
            },
            hasFilter = true,
            dropdownItems = searchFilters
        )

        // Filtered profiles based on search bar text
        val filteredProfiles by remember {
            derivedStateOf {
                if (SEARCH_BAR_TEXT.isEmpty()) {
                    dummyProfileList
                }
                else {
                    dummyProfileList.filter { profile ->
                        profile.name.contains(SEARCH_BAR_TEXT, ignoreCase = true)
                    }
                }
            }
        }

        if (filteredProfiles.isEmpty()) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "No Results")
            }
        } else {
            // Content - Now a LazyColumn
            LazyColumn(
                modifier = Modifier.width(1024.dp), // Make LazyColumn fill the width
                horizontalAlignment = Alignment.CenterHorizontally // Center the items
            ) {
                items(filteredProfiles) { profile ->
                    profilePreview(profile.pfp, profile.name, profile.bio)
                }
            }
        }
    }
}