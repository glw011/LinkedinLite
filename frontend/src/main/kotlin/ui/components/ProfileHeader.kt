package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_banner
import org.jetbrains.compose.resources.painterResource
import ui.theme.MainTheme
import ui.views.openFileChooser

fun editBanner(

) {

}

@Composable
fun ProfileHeader(
    name: String,
    description: String,
    title: String,
    location: String,
    school: String,
    banner: ImageBitmap?,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            // TODO: Add a background image
            Image(
                painter = painterResource(Res.drawable.default_banner),
                contentDescription = "Profile Banner",
                modifier = Modifier.fillMaxWidth().height(128.dp)
            )
            Surface(
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            ) {
                ProfileHeaderDetails(
                    name = name,
                    description = description,
                    title = title,
                    location = location,
                    school = school,
                    modifier = Modifier.fillMaxWidth().wrapContentHeight()
                )
            }
        }
        // Profile picture
        Column(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.TopStart)
                .padding(start = 16.dp)
        ) {
            Spacer(modifier = Modifier.fillMaxWidth().height(64.dp))
            EditablePfpImage(
                imageBitmap = null,
                modifier = Modifier.size(96.dp).border(2.dp, Color.Gray, CircleShape),
                onClick = { val imagePath = openFileChooser() }
            )
        }
        EditButton(
            onClick = {
                // Handle edit button click
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .size(24.dp)
                .clip(CircleShape)
                .align(Alignment.TopEnd)
        )
    }
}

@Composable
fun ProfileHeaderDetails(
    name: String,
    description: String,
    title: String,
    location: String,
    school: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.wrapContentSize()
        ) {
            Column(
                Modifier.weight(1f).wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.fillMaxWidth().height(32.dp))
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight()
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .wrapContentHeight()
                )
                Text(
                    text = location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 16.dp).wrapContentHeight()
                )
            }
            EditButton(
                onClick = {
                    // Handle edit button click
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .size(24.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text( // TODO: Add character limit to bio
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .wrapContentHeight()
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}