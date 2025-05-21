package data

import dao.PostDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.ModelManager
import model.Org
import model.Student
import service.StudentService
import service.UserService
import ui.components.Post
import ui.views.home.ProfileData
import util.getBitmapFromFilepath
import java.util.*

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
                    school = school,
                    id = user.id
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

// Tracks the offset into the full post list across paging calls.
private var currentOffset = 0

// The maximum number of posts to return per page.
private const val PAGE_SIZE = 25

/**
 * Asynchronously generates a page of post UI models for the home feed.
 *
 * This function retrieves all posts from the database, sorts them by newest first,
 * and returns a single page of up to [PAGE_SIZE] posts. Paging is controlled by the
 * [currentOffset] value, which is incremented on each `loadMore` call.
 *
 * If [loadMore] is `false`, the offset is reset to 0 and the first page is returned.
 * If [loadMore] is `true`, the next page is returned starting from the current offset.
 *
 * This function is designed to run on the [Dispatchers.IO] context to avoid blocking
 * the main UI thread, and can be used from within a coroutine scope or Compose effect.
 *
 * @param loadMore Whether this call should fetch the next page (true) or reset and fetch the first page (false).
 * @return A list of [Post] composables representing a slice of the full post feed.
 */
suspend fun genPostList(loadMore: Boolean = false): List<Post> = withContext(Dispatchers.IO) {
    if (!loadMore) currentOffset = 0 // reset if not loading more

    val posts = PostDAO.getAllPosts()
        .sortedByDescending { it.postDate }
        .drop(currentOffset)
        .take(PAGE_SIZE)

    currentOffset += posts.size

    posts.map { post ->
        val imageBitmap = post.postImage?.path?.let { getBitmapFromFilepath(it) }
        val student = StudentService().getStudentById(post.ownerId)
        val userName = listOfNotNull(student.fname, student.lname).joinToString(" ")

        Post(
            postImage = imageBitmap,
            userName = userName,
            description = post.content ?: "",
            comments = LinkedList()
        )
    }
}

fun getCurrentUserProfileImg() {

}