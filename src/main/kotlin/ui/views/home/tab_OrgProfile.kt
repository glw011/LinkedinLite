package ui.views.home

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
import androidx.compose.ui.unit.dp
import data.User
import model.ModelManager
import ui.components.DetailEditDialog
import ui.components.ProfileHeader
import ui.components.dialog.ProfileAssociateEditDialog
import ui.components.dialog.ProfileAssociateInviteDialog
import ui.components.dialog.ProfileTagsEditDialog
import ui.components.profilecard.Associate
import ui.components.profilecard.ProfileAssociatesCard
import ui.components.profilecard.ProfilePostsCard
import ui.components.profilecard.ProfileRecommendationCard
import ui.components.profilecard.ProfileTagsCard
import util.getBitmapFromFilepath
import util.openFileChooser

val headerShape = RoundedCornerShape(16.dp)
val postShape = RoundedCornerShape(8.dp)

/**
 * The profile tab for organizations in the main screen.
 *
 * @param uiState The UI state of the profile.
 */
@Composable
fun OrgProfileTab(
    user: User,
    uiState: ProfileUiState
) {
    val profileHeaderInfo by rememberSaveable { mutableStateOf(ProfileHeaderInfo()) }
    var imagePath by rememberSaveable { mutableStateOf("") }
    var isEditingHeader by rememberSaveable { mutableStateOf(false) }
    var isInvitingMember by rememberSaveable { mutableStateOf(false) }
    var isEditingTags by rememberSaveable { mutableStateOf(false) }
    var memberToEdit: Associate? by rememberSaveable { mutableStateOf(null) }
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
                    tags = uiState.tags,
                    onEdit = { isEditingTags = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                )
                ProfileAssociatesCard(
                    title = "Members",
                    associates = uiState.associates.toMutableList(),
                    onAssociateClick = { member ->
                        memberToEdit = member
                        memberToEditRole = member.role
                    },
                    onAddAssociateClick = { isInvitingMember = true },
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
                    user = user,
                    isPersonalProfile = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                ProfileRecommendationCard(
                    user = user,
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
            onSchoolChanged = { profileHeaderInfo.school = it },
            onSave = {
                if (profileHeaderInfo.name.isNotEmpty()) {
                    uiState.headerInfo.name = profileHeaderInfo.name
                    uiState.user.setName(uiState.headerInfo.name)
                    profileHeaderInfo.name = ""
                }
                if (profileHeaderInfo.description.isNotEmpty()) {
                    uiState.headerInfo.description = profileHeaderInfo.description
                    uiState.user.setDescription(uiState.headerInfo.description)
                    profileHeaderInfo.description = ""
                }
                if (profileHeaderInfo.school.isNotEmpty()) {
                    uiState.user.setSchool(profileHeaderInfo.school)
                    val school = ModelManager.getSchoolByName(profileHeaderInfo.school)
                    uiState.headerInfo.location = "${school.city}, ${school.state}, ${school.country}"
                    uiState.headerInfo.school = profileHeaderInfo.school
                    profileHeaderInfo.school = ""
                }
                isEditingHeader = false
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
        ProfileAssociateInviteDialog(
            user = uiState.user,
            onCancel = { isInvitingMember = false }
        )
    }
    if (memberToEdit != null) {
        ProfileAssociateEditDialog(
            associate = memberToEdit!!,
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
        var tags = rememberSaveable { uiState.tags.toMutableList() }
        println("Tags: $tags")
        ProfileTagsEditDialog(
            tags = tags,
            onSelect = { selectedTags ->
                tags = selectedTags.toMutableList()
            },
            onSave = {
                isEditingTags = false
                uiState.tags = tags
                uiState.user.setTags(tags)
            },
            onCancel = {
                isEditingTags = false
                tags = uiState.tags.toMutableList()
            },
        )
    }
}