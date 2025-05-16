/**
 * UI screen for creating and uploading a new post.
 *
 * This composable allows users to select a photo, add a description (limited to 256 characters),
 * and upload the post. A brief fade-in and fade-out confirmation message is displayed upon successful upload.
 */

package ui.views.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dao.PostDAO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ui.components.styles.styledButton
import ui.components.styles.styledTextField
import ui.theme.LIGHT_PURPLE
import util.getBitmapFromFilepath
import util.openFileChooser
import java.util.*

/**
 * Data class representing a new post with an optional photo and a description.
 */
data class NewPost(
    var photo: ImageBitmap = ImageBitmap(0, 0),
    var description: String = ""
)

/**
 * Composable function representing the post creation UI.
 *
 * Provides functionality for selecting a photo, entering a description (up to 256 characters),
 * and uploading the new post. Displays feedback after upload.
 */
@Composable
fun postTab() {
    var newPost by remember { mutableStateOf(NewPost()) }
    var showMessage by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedVisibility(
            visible = showMessage,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text("Post Uploaded!", color = Color.White, modifier = Modifier.padding(8.dp))
        }

        Spacer(modifier = Modifier.weight(1f))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .width(320.dp)
                    .height(320.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center,
            ) {
                if (newPost.photo == null) {
                    Icon(
                        imageVector = Icons.Filled.AddAPhoto,
                        contentDescription = "Select a Photo",
                        tint = Color.DarkGray,
                        modifier = Modifier
                            .size(64.dp)
                            .clickable {
                                changePhoto { updatedPhoto ->
                                    if (updatedPhoto != null) {
                                        newPost = newPost.copy(photo = updatedPhoto)
                                    }
                                }
                            }
                    )
                } else {
                    Image(
                        bitmap = newPost.photo!!,
                        contentDescription = "Selected Photo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                changePhoto { updatedPhoto ->
                                    if (updatedPhoto != null) {
                                        newPost = newPost.copy(photo = updatedPhoto)
                                    }
                                }
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            styledTextField(
                onTextChanged = {
                    newPost = newPost.copy(description = it.take(255))
                },
                width = 320,
                height = 128,
                unfocusedText = "Description",
                value = newPost.description,
                charLimit = 255
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.padding(8.dp))

        Row {
            Spacer(modifier = Modifier.weight(1f))
            styledButton(
                width = 64,
                onClick = {
                    if (newPost.description.isNotEmpty()) {
                        uploadPost(newPost)
                        newPost = NewPost()
                        coroutineScope.launch {
                            showMessage = true
                            delay(2000)
                            showMessage = false
                        }
                    }
                },
                text = "Post",
                buttonColor = LIGHT_PURPLE,
                textColor = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

/**
 * Opens a file chooser and updates the selected photo.
 *
 * @param onPhotoSelected Callback invoked with the selected [ImageBitmap].
 */
fun changePhoto(onPhotoSelected: (ImageBitmap?) -> Unit) {
    val filepath = openFileChooser()
    val updatedPhoto = getBitmapFromFilepath(filepath)
    onPhotoSelected(updatedPhoto)
}

/**
 * Handles the upload logic for a new post.
 *
 * @param newPost The [NewPost] instance containing photo and description.
 */
fun uploadPost(newPost: NewPost) {
    println("Uploading post: $newPost")
//    PostDAO.pushPost(current_user.getId(), newPost.description, LinkedList())
}
