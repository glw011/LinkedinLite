package ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.Organization
import data.Student
import data.User
import data.getProfilesFromSearch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.ModelManager
import model.UserType
import service.OrgService
import service.StudentService
import ui.components.profilecard.ProfileSlot
import ui.components.searchBar
import ui.components.styles.styledButton
import ui.views.home.ProfileData

/**
 * A dialog for inviting a new member to a profile.
 *
 * @param onCancel Callback for when the cancel button is clicked or dialog is dismissed.
 */
@Composable
fun ProfileAssociateInviteDialog(
    user: User,
    onCancel: () -> Unit,
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    val filteredProfiles by produceState(
        initialValue = emptyList<ProfileData>(),
        searchText
    ) {
        value = withContext(Dispatchers.IO) {
            getProfilesFromSearch(searchText)
        }
    }
    Dialog(
        onDismissRequest = onCancel,
    ) {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f)
                .clip(RoundedCornerShape(16.dp))
        ) {
            val title: String
            if (user.getAccountType() == UserType.STUDENT) {
                title = "Request membership"
            } else {
                title = "Invite a member"
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal=8.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                )
                searchBar(
                    onSearchTextChanged = {
                        searchText = it
                    },
                    alwaysShow = true
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredProfiles.size) { index ->
                        val profile = filteredProfiles[index]
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                        ) {
                            val accountType = ModelManager.getUserType(ModelManager.getUserId(profile.email))
                            if (user.getAccountType() == UserType.STUDENT) {
                                if (accountType == UserType.STUDENT) {
                                    return@Row
                                }
                                val org = Organization.fromModel(OrgService().getOrgById(ModelManager.getUserId(profile.email)))
                                val student = (user as Student)
                                if (org.getId() in student.getOrganizations().map { it.getId() }) {
                                    return@Row
                                }
                                if (student.getId() in org.getPendingMembershipRequests().map { it.getId() }) {
                                    return@Row
                                }
                                ProfileSlot(profile.name, profile.bio, modifier=Modifier.weight(1f))
                                styledButton(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    width = 100,
                                    text = "Request",
                                    onClick = {
                                        student.requestMembership(org)
                                    }
                                )
                            } else {
                                if (accountType == UserType.ORG) {
                                    return@Row
                                }
                                val student = Student.fromModel(StudentService().getStudentById(ModelManager.getUserId(profile.email)))
                                val org = (user as Organization)
                                if (student.getId() in org.getMembers().map { it.getId() }) {
                                    return@Row
                                }
                                if (org.getId() in student.getPendingInvites().map { it.getId() }) {
                                    return@Row
                                }
                                ProfileSlot(profile.name, profile.bio, modifier=Modifier.weight(1f))
                                styledButton(
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    width = 100,
                                    text = "Invite",
                                    onClick = {
                                        org.inviteMember(student)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}