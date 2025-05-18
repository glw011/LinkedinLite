package debug

import androidx.compose.runtime.mutableStateListOf
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