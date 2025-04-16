package ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.onClick
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import ui.components.ProfileCard
import ui.components.ProfileHeader
import ui.components.ProfilePostsCard
import data.DataSource.tags
import ui.ProfileViewModel
import ui.components.DetailEditDialog
import ui.components.PfpImage
import ui.components.ProfileMembersCard
import ui.components.ProfileRecommendationCard
import ui.components.ProfileTagsCard
import ui.components.getBitmapFromFilePath

val headerShape = RoundedCornerShape(16.dp)
val postShape = RoundedCornerShape(8.dp)

val exampleTags = listOf(tags[0], tags[1], tags[2], tags[9], tags[11], tags[12], tags[14], tags[4], tags[5])
val exampleMembers = listOf(
    "Harrison Day",
    "Jayden Toussaint",
    "Chris Leblanc",
    "Alice Johnson",
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

@Composable
fun OrgProfileTab() {
    var banner by rememberSaveable { mutableStateOf<ImageBitmap?>(null) }
    var profilePicture by rememberSaveable { mutableStateOf<ImageBitmap?>(null) }
    var imagePath by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("Organization Name") }
    var description by rememberSaveable { mutableStateOf("\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"") }
    var location by rememberSaveable { mutableStateOf("Organization Location") }
    var school by rememberSaveable { mutableStateOf("Organization School") }

    var tempName by rememberSaveable { mutableStateOf("") }
    var tempDescription by rememberSaveable { mutableStateOf("") }
    var tempLocation by rememberSaveable { mutableStateOf("") }
    var tempSchool by rememberSaveable { mutableStateOf("") }

    var isEditingHeader by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ProfileHeader(
                    name = name,
                    description = description,
                    title = "Organization Title",
                    location = location,
                    school = school,
                    banner = banner,
                    profilePicture = profilePicture,
                    onEditHeader = { isEditingHeader = true },
                    onEditBanner = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            banner = getBitmapFromFilePath(imagePath)
                        }
                    },
                    onEditProfilePicture = {
                        imagePath = openFileChooser()
                        if (imagePath.isNotEmpty()) {
                            profilePicture = getBitmapFromFilePath(imagePath)
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
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp),
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
            onNameChanged = { tempName = it },
            onDescriptionChanged = { tempDescription = it },
            onLocationChanged = { tempLocation = it },
            onSchoolChanged = { tempSchool = it },
            onSave = {
                isEditingHeader = false

                if (tempName.isNotEmpty()) {
                    name = tempName
                    tempName = ""
                }
                if (tempDescription.isNotEmpty()) {
                    description = tempDescription
                    tempDescription = ""
                }
                if (tempLocation.isNotEmpty()) {
                    location = tempLocation
                    tempLocation = ""
                }
                if (tempSchool.isNotEmpty()) {
                    school = tempSchool
                    tempSchool = ""
                }
             },
            onCancel = {
                isEditingHeader = false
                tempName = ""
                tempDescription = ""
                tempSchool = ""
            }
        )
    }
}