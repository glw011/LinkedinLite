package data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.ModelManager
import model.Org
import model.Student
import service.UserService
import ui.views.home.ProfileData

/**
 * Asynchronously searches the database for users (students or organizations) whose names
 * begin with the provided search string. The search is case-insensitive and uses a
 * starts-with match against first name, last name, full name (for students), or
 * organization name (for orgs).
 *
 * This function is designed to be run off the main thread (e.g., using Dispatchers.IO)
 * to avoid blocking the UI. It is safe to call from within a coroutine and integrates
 * well with Compose’s `produceState` or `LaunchedEffect`.
 *
 * Filtering logic is applied after retrieving results from the backend, ensuring that
 * partial or improperly matched results are excluded from display.
 *
 * @param searchText The user-entered search query. Should be non-blank for meaningful results.
 * @return A list of [ProfileData] objects representing the matched users, or an empty list
 *         if no users matched the search criteria.
 *
 * @see kotlinx.coroutines.Dispatchers.IO
 */
suspend fun getProfilesFromSearch(searchText: String): List<ProfileData> = withContext(Dispatchers.IO) {
    val lowerSearch = searchText.lowercase()
    val rawResults = UserService().searchProfiles(lowerSearch)

    rawResults.mapNotNull { user ->
        when (user) {
            is Student -> {
                val fname = user.fname?.lowercase() ?: ""
                val lname = user.lname?.lowercase() ?: ""
                val fullname = (fname + " " + lname)
//                val interests = user.interestList.mapNotNull { id ->
//                    ModelManager.getInterest(id)?.name
//                }
                val major = ModelManager.getMajor(user.major)?.name ?: ""
                val school = ModelManager.getSchool(user.school.schoolId)?.name ?: ""


                if (
                    !fname.startsWith(lowerSearch) &&
                    !lname.startsWith(lowerSearch) &&
                    !fullname.startsWith(lowerSearch)
                ) return@mapNotNull null

                ProfileData(
                    pfp = null,
                    name = "${user.fname} ${user.lname}",
                    bio = user.bio ?: "",
//                    tags = interests,
                    major = major,
                    school = school
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