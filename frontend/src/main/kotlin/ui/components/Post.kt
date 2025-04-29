package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.example.linkedinliteui.generated.resources.default_post
import org.jetbrains.compose.resources.painterResource
import ui.theme.LIGHT_PURPLE

data class Comment (
    val userName: String = "",
    val text: String = "",
    val pfp: ImageBitmap?
)

data class Post (
    val title: String = "",
    val postImage: ImageBitmap?,
    val userName: String = "",
    val description: String = "",
    val comments: List<Comment>
)

@Composable
fun drawPost(post: Post) {
    var liked = remember { mutableStateOf(false) }
    var likeButtonTint = remember { mutableStateOf(Color.Gray) }
    var commentsOpened = remember { mutableStateOf(false) }
    var commentButtonTint = remember { mutableStateOf(Color.Gray) }

    if (liked.value) likeButtonTint.value = LIGHT_PURPLE
    else likeButtonTint.value = Color.Gray

    if (commentsOpened.value) commentButtonTint.value = LIGHT_PURPLE
    else commentButtonTint.value = Color.Gray

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
            if (!commentsOpened.value) {
                Column() {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(15f)
                            .background(Color.LightGray) // White background color
                    ) {
                        Row(
                            modifier = Modifier.padding(start = 8.dp).fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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

                            Text(text = post.userName, fontStyle = Typography().h3.fontStyle)
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
                        } else {
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
                            .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(modifier = Modifier) {
                                Text(post.title, fontStyle = Typography().h3.fontStyle)
                                Spacer(modifier = Modifier.weight(1f))
                                Icon(
                                    imageVector = Icons.Filled.ThumbUp,
                                    contentDescription = "Like",
                                    tint = likeButtonTint.value,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable { liked.value = !liked.value }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.ChatBubble,
                                    contentDescription = "Comments",
                                    tint = commentButtonTint.value,
                                    modifier = Modifier
                                        .size(18.dp)
                                        .clickable { commentsOpened.value = !commentsOpened.value }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(
                                    imageVector = Icons.Filled.Share,
                                    contentDescription = "Share",
                                    tint = Color.Gray,
                                    modifier = Modifier
                                        .size(18.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row() {
                                Text(post.description, fontStyle = Typography().body1.fontStyle)
                            }
                        }
                    }
                }
            }
            else {
                Column() {
                    Row(modifier = Modifier.padding(top = 8.dp, start = 8.dp).fillMaxWidth()) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Comments",
                            tint = commentButtonTint.value,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { commentsOpened.value = !commentsOpened.value }
                        )
                    }
                    if (post.comments.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp)
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            items(post.comments) { comment ->
                                drawComment(comment)
                            }
                        }
                    }
                    else {
                        Row(modifier = Modifier.padding(top = 8.dp, start = 8.dp).fillMaxWidth()) {
                            Text(
                                text = "No comments yet.",
                                textAlign = TextAlign.Center,
                                fontStyle = Typography().body1.fontStyle
                            )
                        }
                    }

                    Row(modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp).fillMaxWidth()) {
                        var commentText = remember { mutableStateOf("") }
                        styledTextField(
                            onTextChanged = {commentText},
                            fillMaxWidth = true,
                            canType = true,
                            unfocusedText = "Add a comment...",
                            icon = Icons.Filled.ArrowForward,
                            iconAlignment = Alignment.CenterEnd,
                            showIcon = true,
                            iconClickedFunctionPtr = {postComment(post)}
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun drawComment(comment: Comment) {
    Column(modifier = Modifier) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically) {
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
            Text(text = comment.userName, fontStyle = Typography().h3.fontStyle)
        }
        Text(text = comment.text, fontStyle = Typography().body1.fontStyle)
    }
}

/** @TODO
 * post comment functionality
 */
fun postComment(post: Post) {
    println("Post Comment Test")
}

/** @TODO
 * like post functionality
 */
fun likePost(post: Post) {
    println("Like Post ${post.title}")
}

fun retrievePost(): Post? {
    return null
}