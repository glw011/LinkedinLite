package debug

import androidx.compose.runtime.mutableStateListOf
import ui.components.Comment
import ui.components.Post
import ui.views.home.ProfileData
import util.getBitmapFromFilepath
import java.nio.file.Paths

/**
 * Loads the default post image from the local file path.
 *
 * This image is used as a placeholder when a post does not have an associated image.
 */
val defaultPost = getBitmapFromFilepath(Paths.get("").toAbsolutePath().toString() + "/src/main/composeResources/drawable/default_post.jpg")

/**
 * Generates a list of dummy profile data for demonstration purposes.
 *
 * This list includes both user and organization profiles, with placeholder names and bios.
 * The generated list is shuffled to provide a randomized order of profiles.
 *
 * @return A shuffled list of [ProfileData] objects.
 */
fun getDummyProfileList(): List<ProfileData> {
    val dummyProfileList = mutableStateListOf<ProfileData>().apply {
        // Generate 30 user profiles
        repeat(30) { i ->
            val name = "user_${(i * 2) + 1000}"
            val bio = "Made up bio for user ${(i * 2) + 1000}, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."
            add(ProfileData(null, name, bio))
        }

        // Generate 5 organization profiles
        repeat(5) { i ->
            val name = "org_${(i * 2) + 1000}"
            val bio = "Made up bio for organization ${(i * 2) + 1000}, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."
            add(ProfileData(null, name, bio))
        }
        // Add a specific organization profile
        add(ProfileData(null, "LATech AI", "Made up bio for organization LATech AI, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."))
    }.shuffled() // Shuffle the list to randomize the order

    return dummyProfileList
}

/**
 * Generates a list of dummy post data for demonstration purposes.
 *
 * This list includes a mix of posts with and without images.
 * Each post has a title, an optional image, a user, a description, and a list of comments.
 * The generated list is shuffled to provide a randomized order of posts.
 *
 * @return A shuffled list of [Post] objects.
 */
val dummyPostList = mutableStateListOf<Post>().apply {
    // Generate 5 posts without images
    repeat(5) { i ->
        val user = "user_${(i * 2) + 1000}"
        val desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the description. Adding more text to see if the posts support dynamic height..."
        val coms = mutableStateListOf<Comment>().apply {
            repeat(15) { i ->
                add(Comment("user ${i + 1}", "test comment, a bunch of useless text to fill the comment. Testing to see if the comments support variable height...", null))
            }
        }
        add(Post("Post Title", null, user, desc, coms))
    }

    // Generate 5 posts with images
    repeat(5) { i ->
        val user = "user_${(i * 3) + 1000}"
        val desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the description."
        val coms = mutableStateListOf<Comment>().apply {
            repeat(15) { i ->
                add(Comment("user ${i + 1}", "test comment, a bunch of useless text to fill the comment. Testing to see if the comments support variable height...", null))
            }
        }
        add(Post("Post Title", defaultPost, user, desc, coms))
    }
}.shuffled() // Shuffle the list to randomize the order