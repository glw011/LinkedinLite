package ui.views.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.genPostList
import ui.components.drawPost

/**
 * Composable function that displays the Home tab UI, consisting of a scrollable feed of posts.
 *
 * Posts are initially loaded asynchronously from the database using [genPostList].
 * When the user scrolls to the bottom of the list, additional posts are dynamically loaded.
 * While loading is in progress, a "Loading..." message is displayed below the feed.
 *
 * This function uses [LazyColumn] to efficiently render a vertically scrollable list of posts,
 * and uses [rememberLazyListState] along with [snapshotFlow] to detect when the user has
 * reached the bottom of the list and trigger pagination.
 *
 * State Variables:
 * - `posts`: The list of currently loaded posts.
 * - `isLoading`: Whether posts are currently being loaded.
 * - `endReached`: Whether there are no more posts to load.
 *
 * Behavior:
 * - Loads an initial page of posts when the composable first launches.
 * - Continues to load additional pages when the user scrolls to the bottom,
 *   until the end of the post list is reached.
 */
@Composable
fun homeTab() {
    val listState = rememberLazyListState()
    var posts by remember { mutableStateOf<List<ui.components.Post>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var endReached by remember { mutableStateOf(false) }

    // Initial load
    LaunchedEffect(Unit) {
        isLoading = true
        posts = genPostList(loadMore = false)
        isLoading = false
    }

    // Scroll-to-bottom load
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo }
            .collect { layout ->
                val lastVisible = layout.visibleItemsInfo.lastOrNull()?.index ?: -1
                val totalItems = layout.totalItemsCount
                val reachedBottom = lastVisible >= totalItems - 1 && totalItems > 0

                if (reachedBottom && !isLoading && !endReached) {
                    isLoading = true
                    val newPosts = genPostList(loadMore = true)
                    if (newPosts.isEmpty()) {
                        endReached = true // Nothing left to load
                    } else {
                        posts = posts + newPosts
                    }
                    isLoading = false
                }
            }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(posts) { post ->
                drawPost(post)
            }
        }

        // Only show loading if more can be loaded
        if (isLoading && !endReached) {
            Text(
                text = "Loading...",
                modifier = Modifier.padding(8.dp),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}