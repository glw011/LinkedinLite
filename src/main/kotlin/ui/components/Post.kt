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
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource
import ui.components.styles.styledTextField
import ui.theme.LIGHT_PURPLE
import java.util.*

/**
 * Represents a comment on a post in the application.
 *
 * @property userName The name of the user who made the comment
 * @property text The content of the comment
 * @property pfp The profile picture of the commenter as an ImageBitmap, can be null
 */
data class Comment(
    val userName: String = "",
    val text: String = "",
    val pfp: ImageBitmap?
)

/**
 * Represents a post in the application.
 *
 * @property postImage The image attached to the post, can be null
 * @property userName The name of the user who created the post
 * @property description The main content/description of the post
 * @property comments List of comments associated with the post
 */
data class Post(
    val postImage: ImageBitmap?,
    val userName: String = "",
    val description: String = "",
    val comments: LinkedList<Comment> = LinkedList()
)

/**
 * Renders a post with its associated content and interactive elements.
 *
 * @param post The post to be displayed
 */
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
        val boxModifier = if (post.postImage != null) {
            if (!commentsOpened.value) {
                Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            } else {
                Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .aspectRatio(0.75f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            }
        } else {
            if (!commentsOpened.value) {
                Modifier
                    .fillMaxWidth(0.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            } else {
                Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .aspectRatio(0.75f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            }
        }

        Box(
            modifier = boxModifier
        ) {
            if (!commentsOpened.value) {
                Column() {
                    if (post.postImage != null) {
                        Spacer(modifier = Modifier.weight(1f))

                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                alignment = Alignment.Center,
                                painter = BitmapPainter(post.postImage),
                                contentDescription = "Post Image",
                                contentScale = ContentScale.Crop
                            )
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
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        modifier = Modifier
                                            .height(16.dp)
                                            .width(16.dp)
                                            .clip(CircleShape),
                                        alignment = Alignment.Center,
                                        painter = painterResource(Res.drawable.default_pfp),
                                        contentDescription = "Profile Picture",
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = post.userName,
                                        fontStyle = Typography().h3.fontStyle
                                    )
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
                                            .clickable {
                                                commentsOpened.value = !commentsOpened.value
                                            }
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
                    else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
//                                .fillMaxHeight(0.1f)
//                                .aspectRatio(1.75f)
                                .background(Color.LightGray) // White background color
                                .padding(top = 8.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Image(
                                        modifier = Modifier
                                            .height(16.dp)
                                            .width(16.dp)
                                            .clip(CircleShape),
                                        alignment = Alignment.Center,
                                        painter = painterResource(Res.drawable.default_pfp),
                                        contentDescription = "Profile Picture",
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = post.userName,
                                        fontStyle = Typography().h3.fontStyle
                                    )
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
                                            .clickable {
                                                commentsOpened.value = !commentsOpened.value
                                            }
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
                            iconClickedFunctionPtr = {postComment(post)},
                            height = 33
                        )
                    }
                }
            }
        }
    }
}

/**
 * Renders a comment with the user's profile picture and content.
 *
 * @param comment The comment to be displayed
 */
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

/**
 * Posts a comment on the specified post.
 *
 * @param post The post to comment on
 * @TODO Implement actual comment posting functionality
 */
fun postComment(post: Post) {
    println("Post Comment Test")
}

/**
 * Toggles the like status for the specified post.
 *
 * @param post The post to like/unlike
 * @TODO Implement actual like functionality with backend integration
 */
fun likePost(post: Post) {
    println("Like Post")
}

/**
 * Retrieves a post from the data source.
 *
 * @return The retrieved post or null if no post is found
 * @TODO Implement actual post retrieval from backend
 */
fun retrievePost(): Post? {
    return null
}