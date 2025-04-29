package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.example.linkedinliteui.generated.resources.default_post
import org.jetbrains.compose.resources.painterResource

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

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.5f)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White), // White background color
        ) {
            Column() {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(15f)
                        .background(Color.LightGray) // White background color
                ) {
                    Row(modifier = Modifier.padding(start = 8.dp).fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier
                                .fillMaxHeight(0.75f)
                                .aspectRatio(1f)
                                .clip(CircleShape),
                            alignment = Alignment.Center,
                            painter = painterResource(Res.drawable.default_pfp),
                            contentDescription = "Profile Picture",
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(text = post.userName)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(modifier = Modifier.fillMaxSize()) {
                    if (post.postImage != null) {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            alignment = Alignment.Center,
                            painter = BitmapPainter(post.postImage),
                            contentDescription = "Post Image",
                            contentScale = ContentScale.Crop
                        )
                    }

                    else {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            alignment = Alignment.TopCenter,
                            painter = painterResource(Res.drawable.default_post),
                            contentDescription = "Post Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.1f)
                        .aspectRatio(1.75f)
                        .background(Color.LightGray) // White background color
                ) {
                    Row(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
                        Text(post.description)
                    }
                }
            }
        }
    }
}

fun retrievePost(): Post? {
    return null
}