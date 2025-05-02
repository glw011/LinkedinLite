package ui.components.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ui.components.Field
import ui.components.profilecard.Member

@Composable
fun ProfileMemberEditDialog(
    member: Member,
    onRoleChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    EditDialog(
        title = "Edit ${member.name}",
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