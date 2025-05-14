package data

import model.Org
import model.Student
import service.UserService
import ui.views.home.ProfileData

/**
 * Searches the database for users (students or organizations) whose names start with the given text.
 * Converts each match into a [ProfileData] object for UI rendering.
 *
 * @param searchText The search string entered by the user. Matching is case-insensitive and starts-with based.
 * @return A list of [ProfileData] containing user display data for each matching student or organization.
 */
fun getProfilesFromSearch(searchText: String): List<ProfileData> {
    val lowerSearch = searchText.lowercase()
    val rawResults = UserService().searchProfiles(lowerSearch)

    return rawResults.mapNotNull { user ->
        when (user) {
            is Student -> {
                val fname = user.fname?.lowercase() ?: ""
                val lname = user.lname?.lowercase() ?: ""

                // Optional: skip non-matches if DAO is ever relaxed to wider matches
                if (!fname.startsWith(lowerSearch) && !lname.startsWith(lowerSearch)) return@mapNotNull null

                ProfileData(
                    pfp = null,
                    name = "${user.fname} ${user.lname}",
                    bio = user.bio ?: "",
                    tags = emptyList()
                )
            }

            is Org -> {
                val orgName = user.name?.lowercase() ?: ""
                if (!orgName.startsWith(lowerSearch)) return@mapNotNull null

                ProfileData(
                    pfp = null,
                    name = user.name,
                    bio = user.bio ?: "",
                    tags = emptyList()
                )
            }

            else -> null
        }
    }
}