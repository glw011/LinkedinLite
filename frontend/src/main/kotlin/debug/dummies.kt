package debug

import androidx.compose.runtime.mutableStateListOf
import ui.components.Comment
import ui.components.Post
import ui.views.home.ProfileData

fun getDummyProfileList(): List<ProfileData> {
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
        add(ProfileData(null, "LATech AI", "Made up bio for organization LATech AI, Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."))
    }.shuffled()

    return dummyProfileList
}

fun getDummyPostsList(): List<Post> {
    val dummyPostList = mutableStateListOf<Post>().apply {
        repeat(30) { i ->
            val user = "no_user"
            val desc = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Arbitrary text to fill up the bio."
            val coms = mutableStateListOf<Comment>().apply {
                repeat(15) { i ->
                    add(Comment("user ${i + 1}", "test comment, a bunch of useless text to fill the comment", null))
                }
            }
            add(Post(null, user, desc, coms))
        }
    }.shuffled()

    return dummyPostList
}