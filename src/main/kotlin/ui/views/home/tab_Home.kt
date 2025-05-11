package ui.views.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import debug.dummyPostList
import debug.generateDummies
import ui.components.drawPost

/**
 * The home screen composable that displays a feed of posts.
 *
 * This function builds a vertically scrolling list (`LazyColumn`) of posts,
 * dynamically populating the list with either dummy data or, eventually, real data.
 *
 * The `generateDummies` flag determines whether to use the `dummyPostList`.
 * Currently, even when `generateDummies` is `false`, the `dummyPostList` is used as
 * placeholder until real data loading is implemented.
 *
 */
@Composable
fun homeTab() {
    /**
     * The list of posts to display.
     *
     * If `generateDummies` is true, it uses the dummy data, otherwise it uses a placeholder
     * dummy list until real data loading is implemented.
     */
    val postList = if (generateDummies)
        dummyPostList
    else
        dummyPostList // Same for now, until we get real data

    // Feed
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /**
         * Displays each post in the `postList`.
         *
         * Uses the `drawPost` composable to render each individual post.
         */
        items(postList) { post ->
            drawPost(post)
        }
    }
}