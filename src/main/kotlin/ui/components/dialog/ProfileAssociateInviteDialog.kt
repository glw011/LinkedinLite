package ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * A dialog for inviting a new member to a profile.
 *
 * @param onCancel Callback for when the cancel button is clicked or dialog is dismissed.
 */
@Composable
fun ProfileAssociateInviteDialog(
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
            Text(
                text = "Invite a member",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
            )
        }
    }
}