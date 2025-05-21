package ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ui.components.Field
import ui.components.profilecard.Associate

/**
 * A dialog for editing a member's role in a profile.
 *
 * @param member The member to edit.
 * @param onRoleChange Callback for when the role is changed.
 * @param onSave Callback for when the save button is clicked.
 * @param onCancel Callback for when the cancel button is clicked or dialog is dismissed.
 */
@Composable
fun ProfileAssociateEditDialog(
    associate: Associate,
    onRoleChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    EditDialog(
        title = "Edit ${associate.name}",
        fields = listOf(
            Field(
                title = "Role",
                prompt = "Enter new role",
                onEdit = onRoleChange,
            )
        ),
        onCancel = onCancel,
        onConfirm = onSave,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .wrapContentHeight()
            .clip(RoundedCornerShape(16.dp))
    )
}