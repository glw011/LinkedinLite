/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dao.PictureDAO
import dao.UserDAO
import data.DataSource.searchFilters
import data.getProfilesFromSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ui.components.profilePreview
import ui.components.searchActive
import ui.components.searchBar
import util.getBitmapFromFilepath

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
    val tags: List<String> = listOf(),
    val email: String = "",
    val major: String = "",
    val school: String = "",
    val id: Int = 0
)

/**
 * Composable UI function for the "People & Organizations" tab.
 *
 * This screen provides a searchable interface for browsing users (students or organizations).
 * It includes:
 * - A search bar for entering query text (updates [SEARCH_BAR_TEXT]).
 * - Optional filtering via dropdown items.
 * - Dynamically rendered results from [getProfilesFromSearch] based on the query.
 * - Smart UI states:
 *   - A prompt when no query is entered.
 *   - A debounced loading indicator while searching.
 *   - A "No Results" message if the query returns nothing.
 *   - A scrollable list of matching profiles if results are found.
 *
 * The search query is debounced (500ms) to prevent excessive database requests and improve UX.
 * Results are cleared on every query change to avoid displaying stale data.
 *
 * This composable depends on the mutable global [SEARCH_BAR_TEXT] and assumes a reactive Compose
 * state system around it. Intended to be used inside a navigation host or screen container.
 */
@Composable
fun peopleOrgsTabContent() {
    var isLoading by remember { mutableStateOf(false) }

    val filteredProfiles by produceState(
        initialValue = emptyList<ProfileData>(),
        SEARCH_BAR_TEXT
    ) {
        value = emptyList()

        if (SEARCH_BAR_TEXT.isBlank()) {
            isLoading = false
            return@produceState
        }

        isLoading = true
        delay(250)

        if (SEARCH_BAR_TEXT.isBlank()) {
            isLoading = false
            return@produceState
        }

        value = withContext(Dispatchers.IO) {
            getProfilesFromSearch(SEARCH_BAR_TEXT)
        }

        isLoading = false
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (searchActive)
            Spacer(modifier = Modifier.padding(top = 16.dp))

        searchBar(
            onSearchTextChanged = { SEARCH_BAR_TEXT = it },
            hasFilter = true,
            dropdownItems = searchFilters
        )

        when {
            SEARCH_BAR_TEXT.isBlank() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Search for a User or Organization",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "Loading...",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            filteredProfiles.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "No Results",
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.width(1024.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    items(filteredProfiles) { profile ->
                        val imgPath = PictureDAO.getImgPath(UserDAO.getProfileImgId(profile.id))
                        var profileImg: ImageBitmap? = null

                        if (imgPath != null)
                            profileImg = getBitmapFromFilepath(PictureDAO.getImgPath(UserDAO.getProfileImgId(profile.id)))
                        else
                            profileImg = null

                        profilePreview(
                            profileImg,
                            profile.name,
                            profile.bio,
                            profile.major,
                            profile.school,
                            onClick = {
//                                OrgProfileTab(ProfileUiState())
                            })
                    }
                }
            }
        }
    }
}