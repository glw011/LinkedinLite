package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import ui.components.dialog.EditDialog
import ui.components.image.Banner
import ui.components.image.EditablePfpImage

/**
 * Creates a dialog window for editing profile details.
 *
 * @param onNameChanged Callback for when the name field is changed.
 * @param onDescriptionChanged Callback for when the description field is changed.
 * @param onLocationChanged Callback for when the location field is changed.
 * @param onSchoolChanged Callback for when the school field is changed.
 * @param onSave Callback for when the save button is clicked.
 * @param onCancel Callback for when the cancel button is clicked.
 */
@Composable
fun DetailEditDialog(
    onNameChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onLocationChanged: (String) -> Unit,
    onSchoolChanged: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
) {
    val fields: List<Field> = listOf(
        Field(
            title = "Name",
            prompt = "Enter your name",
            onEdit = onNameChanged,
        ),
        Field(
            title = "Description",
            prompt = "Enter your description",
            onEdit = onDescriptionChanged,
        ),
        Field(
            title = "Location",
            prompt = "Enter your location",
            onEdit = onLocationChanged,
        ),
        Field(
            title = "School",
            prompt = "Enter your school",
            onEdit = onSchoolChanged,
        ),
    )

    EditDialog(
        title = "Edit Profile",
        fields = fields,
        onCancel = onCancel,
        onConfirm = onSave,
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .wrapContentHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    )
}

/**
 * Creates a header for a profile. Contains a banner, editable profile picture, and an area for
 * account details. There are two edit buttons, one for editing the banner one for editing the
 * basic profile details.
 *
 * @param name The name of the user.
 * @param description The description/bio of the user.
 * @param title The title of the user (E.g. "Organization" or "Student").
 * @param location The location of the user (City, State).
 * @param school The name of the school of the user.
 * @param banner The imageBitmap of the banner image to be displayed.
 * @param profilePicture The imageBitmap of the profile picture to be displayed.
 * @param onEditHeader Callback for when the edit button for the header is clicked.
 * @param onEditBanner Callback for when the edit button for the banner is clicked.
 * @param onEditProfilePicture Callback for when the profile picture is clicked.
 * @param modifier Modifier to be applied to the header.
 */
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

/**
 * Creates the profile details section of the profile header.
 *
 * @param name The name of the user.
 * @param description The description/bio of the user.
 * @param title The title of the user (E.g. "Organization" or "Student").
 * @param location The location of the user (City, State).
 * @param school The name of the school of the user.
 * @param onEdit Callback for when the edit button is clicked.
 * @param modifier Modifier to be applied to the details section.
 */
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
                    text = "$title at $school",
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