package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.window.Dialog

@Composable
fun DetailEditDialog(
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onSchoolChanged: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                AccountDetailField(
                    label = "Name",
                    prompt = "Enter your name",
                    onTextChanged = onNameChanged,
                )
                AccountDetailField(
                    label = "Description",
                    prompt = "Enter your description",
                    onTextChanged = onDescriptionChanged,
                )
                AccountDetailField(
                    label = "Location",
                    prompt = "Enter your location",
                    onTextChanged = onLocationChanged,
                )
                AccountDetailField(
                    label = "School",
                    prompt = "Enter your school",
                    onTextChanged = onSchoolChanged,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    styledButton(
                        text = "Save",
                        onClick = onSave,
                        width = 80,
                    )
                    styledButton(
                        text = "Cancel",
                        onClick = onCancel,
                        width = 80,
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeader(
    name: String,
    description: String,
    title: String,
    location: String,
    school: String,
    banner: ImageBitmap?,
    profilePicture: ImageBitmap?,
    onEditHeader: () -> Unit,
    onEditBanner: () -> Unit,
    onEditProfilePicture: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
        ) {
            Banner(
                imageBitmap = banner,
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
                    onEdit = onEditHeader,
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
                imageBitmap = profilePicture,
                modifier = Modifier
                    .size(96.dp)
                    .border(2.dp, Color.Gray, CircleShape)
                    .background(Color.Gray, CircleShape),
                onClick = onEditProfilePicture
            )
        }
        EditButton(
            onClick = onEditBanner,
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
    onEdit: () -> Unit,
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
                    text = "Organization at $school",
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
                onClick = onEdit,
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