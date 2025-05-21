package ui.views.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import data.Organization
import data.Student
import data.User
import model.UserType
import ui.components.image.PfpImage
import ui.components.profilecard.Associate
import ui.components.styles.styledButton

@Composable
fun NotificationCard(
    title: String,
    description: String,
    profilePicture: ImageBitmap,
    onAccept: () -> Unit,
    onReject: () -> Unit,
) {
    Row(
        modifier = Modifier.height(96.dp)
            .fillMaxWidth(0.8f)
            .background(MaterialTheme.colorScheme.background),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        PfpImage(
            imageBitmap = profilePicture,
            modifier = Modifier
                .size(96.dp)
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.wrapContentHeight()
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.wrapContentHeight()
            )
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
            ) {
                styledButton(
                    text = "Accept",
                    onClick = onAccept,
                    width = 100,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp, end = 16.dp),
                )
                styledButton(
                    text = "Reject",
                    onClick = onReject,
                    width = 100,
                )
            }
        }
    }
}

@Composable
fun NotificationsTab(
    user: User,
    uiState: NotificationsUiState,
    profileUiState: ProfileUiState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
            ) {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
        if (user.getAccountType() == UserType.STUDENT) {
            uiState.notifications = (user as Student).getPendingInvites().toMutableStateList()
            items(uiState.notifications.size) { index ->
                val organization = uiState.notifications[index] as Organization
                NotificationCard(
                    title = "Invitation",
                    description = "You have been invited to join ${organization.getName()}!",
                    profilePicture = organization.getProfilePicture(),
                    onAccept = {
                        (user as Student).acceptMembership(organization)
                        profileUiState.associates.add(
                            Associate(
                            organization.getEmail(),
                            organization.getName(),
                            organization.getMemberRole(user),
                            organization.getProfilePicture()
                            )
                        )
                        uiState.notifications.remove(organization)
                    },
                    onReject = {
                        (user as Student).rejectMembership(organization)
                        uiState.notifications.remove(organization)
                    },
                )
            }
        } else {
            uiState.notifications = (user as Organization).getPendingMembershipRequests().toMutableStateList()
            items(uiState.notifications.size) { index ->
                val student = uiState.notifications[index] as Student
                NotificationCard(
                    title = "Membership Request",
                    description = "${student.getName()} wants to join your organization!",
                    profilePicture = student.getProfilePicture(),
                    onAccept = {
                        (user as Organization).acceptMember(student)
                        profileUiState.associates.add(
                            Associate(
                                student.getEmail(),
                                student.getName(),
                                (user as Organization).getMemberRole(student),
                                student.getProfilePicture()
                            )
                        )
                        uiState.notifications.remove(student)
                    },
                    onReject = {
                        (user as Organization).rejectMember(student)
                        uiState.notifications.remove(student)
                    },
                )
            }
        }
    }
}