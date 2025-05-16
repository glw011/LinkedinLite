package ui.views.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import ui.components.DetailEditDialog
import ui.components.ProfileHeader
import ui.components.dialog.ProfileMemberEditDialog
import ui.components.dialog.ProfileMemberInviteDialog
import ui.components.dialog.ProfileTagsEditDialog
import ui.components.profilecard.*
import util.getBitmapFromFilepath
import util.openFileChooser

val headerShape = RoundedCornerShape(16.dp)
val postShape = RoundedCornerShape(8.dp)

val exampleNames = listOf(
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
val exampleMembers = exampleNames.mapIndexed { index, name ->
    Member(
        name = name,
        role = exampleRoles[index],
        profilePicture = ImageBitmap(0, 0) // Placeholder for profile picture
    )
}

/**
 * The profile tab for organizations in the main screen.
 *
 * @param uiState The UI state of the profile.
 */
@Composable
fun OrgProfileTab(
    uiState: ProfileUiState
) {
    val profileHeaderInfo by rememberSaveable { mutableStateOf(ProfileHeaderInfo()) }
    var imagePath by rememberSaveable { mutableStateOf("") }
    var isEditingHeader by rememberSaveable { mutableStateOf(false) }
    var isInvitingMember by rememberSaveable { mutableStateOf(false) }
    var isEditingTags by rememberSaveable { mutableStateOf(false) }
    var memberToEdit: Member? by rememberSaveable { mutableStateOf(null) }
    var memberToEditRole: String by rememberSaveable { mutableStateOf("") }

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
                    banner = uiState.headerInfo.banner.value,
                    profilePicture = uiState.headerInfo.profilePicture.value,
                    onEditHeader = { isEditingHeader = true },
                    onEditBanner = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            uiState.headerInfo.banner.value = getBitmapFromFilepath(imagePath)
                        }
                    },
                    onEditProfilePicture = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            uiState.headerInfo.profilePicture.value = getBitmapFromFilepath(imagePath)
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
                    tags = uiState.tags,
                    onEdit = { isEditingTags = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                )
                ProfileMembersCard(
                    title = "Members",
                    members = mutableStateListOf(*exampleMembers.toTypedArray()),
                    onMemberClick = { member ->
                        memberToEdit = member
                        memberToEditRole = member.role
                    },
                    onAddMemberClick = { isInvitingMember = true },
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
    if (isInvitingMember) {
        ProfileMemberInviteDialog(
            onCancel = { isInvitingMember = false }
        )
    }
    if (memberToEdit != null) {
        ProfileMemberEditDialog(
            member = memberToEdit!!,
            onRoleChange = { memberToEditRole = it },
            onSave = {
                memberToEdit?.role = memberToEditRole
                memberToEdit = null
                memberToEditRole = ""
            },
            onCancel = {
                memberToEdit = null
                memberToEditRole = ""
            },
        )
    }
    if (isEditingTags) {
        var tags = rememberSaveable { mutableStateListOf(*uiState.tags.toTypedArray()) }
        ProfileTagsEditDialog(
            tags = tags,
            onSelect = { selectedTag ->
                if (selectedTag in tags) {
                    tags.remove(selectedTag)
                } else {
                    tags.add(selectedTag)
                }
            },
            onSave = {
                isEditingTags = false
                uiState.tags = tags
            },
            onCancel = {
                isEditingTags = false
                tags = uiState.tags as SnapshotStateList<String>
            },
        )
    }
}