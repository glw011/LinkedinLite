package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import util.ScreenDimensions

data class Comment (
    val userName: String = "",
    val text: String = "",
    val pfp: ImageBitmap?
)

data class Post (
    val postImage: ImageBitmap?,
    val userName: String = "",
    val description: String = "",
    val comments: List<Comment>
)

@Composable
fun drawPost(post: Post) {
    Spacer(modifier = Modifier.height(64.dp))
    println(ScreenDimensions.height)

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.White), // White background color
        )
    }
}

fun retrievePost(): Post? {
    return null
}