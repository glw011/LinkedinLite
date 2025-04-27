package ui.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import debug.generateDummies
import debug.getDummyPostsList
import ui.components.drawPost

@Composable
fun homeTab() {
    val postList = if (generateDummies)
        getDummyPostsList()
    else
        getDummyPostsList() // Same for now, until we get real data

    // Feed
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(postList) { post ->
            drawPost(post)
        }
    }
}
