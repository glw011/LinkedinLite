package ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import data.DataSource.tags
import ui.ProfileHeaderInfo
import ui.ProfileUiState
import ui.components.DetailEditDialog
import ui.components.ProfileHeader
import ui.components.ProfileMembersCard
import ui.components.ProfilePostsCard
import ui.components.ProfileRecommendationCard
import ui.components.ProfileTagsCard
import util.getBitmapFromFilepath

val headerShape = RoundedCornerShape(16.dp)
val postShape = RoundedCornerShape(8.dp)

val exampleTags = listOf(tags[0], tags[1], tags[2], tags[9], tags[11], tags[12], tags[14], tags[4], tags[5])
val exampleMembers = listOf(
    "Harrison Day",
    "Jayden Toussaint",
    "Chris Leblanc",
    "Garrett Williams",
    "Bob Brown",
    "Charlie Davis",
    "Diana Evans",
    "Ethan Foster",
    "Fiona Green"
)
val exampleRoles = listOf(
    "President",
    "Vice President",
    "Secretary",
    "Treasurer",
    "Member",
    "Member",
    "Member",
    "Member",
    "Member"
)

/**
 * The profile tab for organizations in the main screen.
 */
@Composable
fun OrgProfileTab(
    uiState: ProfileUiState
) {
    var profileHeaderInfo by rememberSaveable { mutableStateOf(ProfileHeaderInfo()) }
    var imagePath by rememberSaveable { mutableStateOf("") }
    var isEditingHeader by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ProfileHeader(
                    name = uiState.headerInfo.name,
                    description = uiState.headerInfo.description,
                    title = uiState.headerInfo.title,
                    location = uiState.headerInfo.location,
                    school = uiState.headerInfo.school,
                    banner = uiState.headerInfo.banner,
                    profilePicture = uiState.headerInfo.profilePicture,
                    onEditHeader = { isEditingHeader = true },
                    onEditBanner = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            uiState.headerInfo.banner = getBitmapFromFilepath(imagePath)
                        }
                    },
                    onEditProfilePicture = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            uiState.headerInfo.profilePicture = getBitmapFromFilepath(imagePath)
                        }
                    },
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, headerShape)
                        .clip(headerShape)
                )
                ProfilePostsCard(
                    title = "Posts",
                    modifier = Modifier
                        .height(650.dp)
                        .fillMaxWidth()
                )
                ProfileTagsCard(
                    title = "Tags",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                )
                ProfileMembersCard(
                    title = "Members",
                    members = exampleMembers,
                    roles = exampleRoles,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                // Temporary: To be replaced by ProfileRecommendations
                ProfileRecommendationCard(
                    isPersonalProfile = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                ProfileRecommendationCard(
                    isPersonalProfile = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
            }
        }
    }
    if (isEditingHeader) {
        DetailEditDialog(
            onNameChanged = { profileHeaderInfo.name = it },
            onDescriptionChanged = { profileHeaderInfo.description = it },
            onLocationChanged = { profileHeaderInfo.location = it },
            onSchoolChanged = { profileHeaderInfo.school = it },
            onSave = {
                isEditingHeader = false

                if (profileHeaderInfo.name.isNotEmpty()) {
                    uiState.headerInfo.name = profileHeaderInfo.name
                    profileHeaderInfo.name = ""
                }
                if (profileHeaderInfo.description.isNotEmpty()) {
                    uiState.headerInfo.description = profileHeaderInfo.description
                    profileHeaderInfo.description = ""
                }
                if (profileHeaderInfo.location.isNotEmpty()) {
                    uiState.headerInfo.location = profileHeaderInfo.location
                    profileHeaderInfo.location = ""
                }
                if (profileHeaderInfo.school.isNotEmpty()) {
                    uiState.headerInfo.school = profileHeaderInfo.school
                    profileHeaderInfo.school = ""
                }
             },
            onCancel = {
                isEditingHeader = false
                profileHeaderInfo.name = ""
                profileHeaderInfo.description = ""
                profileHeaderInfo.location = ""
                profileHeaderInfo.school = ""
            }
        )
    }
}