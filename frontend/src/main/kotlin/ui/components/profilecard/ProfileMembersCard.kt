package ui.components.profilecard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.CloseButton
import ui.components.image.PfpImage

/**
 * Creates a surface composable containing a profile picture and member details
 * of members of an organization for displaying on a profile.
 *
 * @param name The name of the member.
 * @param role The role of the member.
 * @param profilePicture The profile picture of the member.
 * @param onClick The callback for when the member is clicked.
 * @param modifier The modifier for the surface.
 */
@Composable
private fun MemberDetails(
    name: String,
    role: String,
    profilePicture: ImageBitmap?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        PfpImage(
            imageBitmap = profilePicture,
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentScale = ContentScale.FillHeight
        )
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = role,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}

/**
 * Creates a member slot for displaying a member's profile picture, name, and role.
 * The slot is clickable and can be edited if specified.
 *
 * @param name The name of the member.
 * @param role The role of the member.
 * @param profilePicture The profile picture of the member.
 * @param isEditable Whether the slot is editable or not.
 * @param onDelete The callback for when the delete button is clicked.
 * @param onClick The callback for when the slot is clicked.
 * @param modifier The modifier for the slot.
 */
@Composable
private fun MemberSlot(
    name: String,
    role: String,
    profilePicture: ImageBitmap?,
    isEditable: Boolean = false,
    onDelete: () -> Unit = {},
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
    ) {
        MemberDetails(
            name = name,
            role = role,
            profilePicture = profilePicture,
            onClick = onClick,
        )
        if (isEditable) {
            CloseButton(
                onClick = onDelete,
                modifier = Modifier
                    .padding(8.dp)
                    .size(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.7f),
                        shape = CircleShape
                    )
                    .align(Alignment.TopEnd)
            )
        }
    }
}

/**
 * A card to be placed on a profile page that displays a list of members with
 * their names and roles.
 *
 * @param title The title of the card.
 * @param subtitle The subtitle of the card.
 * @param members A list of members to be displayed.
 * @param onMemberClick The callback for when a member is clicked.
 * @param onAddMemberClick The callback for when the add member button is clicked.
 * @param modifier Modifier to apply to the card.
 */
@Composable
fun ProfileMembersCard(
    title: String,
    subtitle: String = "",
    members: MutableList<Member>,
    onMemberClick: (Member) -> Unit,
    onAddMemberClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditing by rememberSaveable{ mutableStateOf(false) }
    val editIcon = if (isEditing) Icons.Default.Close else Icons.Default.Edit
    ProfileCard(
        title = title,
        subtitle = subtitle,
        editIcon = editIcon,
        onClick = {
            isEditing = !isEditing
        },
        modifier = modifier
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val itemModifier = Modifier
                .width(175.dp)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))

            items(members.size) { index ->
                MemberSlot(
                    name = members[index].name,
                    role = members[index].role,
                    profilePicture = members[index].profilePicture,
                    isEditable = isEditing,
                    onClick = {
                        if (isEditing) {
                            onMemberClick(members[index])
                        } else {
                            // Handle view member action
                        }
                    },
                    onDelete = {
                        members.remove(members[index])
                    },
                    modifier = itemModifier
                )
            }

            item {
                Box(
                    modifier = itemModifier
                        .clickable(onClick = { onAddMemberClick() })
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Member",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}