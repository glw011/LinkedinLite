package ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ui.components.AccountDetailField
import ui.components.Field
import ui.components.styles.styledButton

/**
 * A dialog that allows the user to edit their account information.
 *
 * @param title The title of the dialog.
 * @param fields The list of fields to be displayed in the dialog.
 * @param onCancel Callback function for when the user clicks the cancel button.
 * @param onConfirm Callback function for when the user clicks the confirm button.
 * @param modifier Optional modifier for styling.
 */
@Composable
fun EditDialog(
    title: String,
    fields: List<Field>,
    otherContent: @Composable (() -> Unit)? = null,
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onCancel
    ) {
        ElevatedCard(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Fields
                for (field in fields) {
                    AccountDetailField(
                        label = field.title,
                        prompt = field.prompt,
                        onTextChanged = field.onEdit,
                    )
                }

                // Other content
                otherContent?.let { it() }

                Spacer(modifier = Modifier.height(16.dp))

                // Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    styledButton(
                        text = "Save",
                        onClick = onConfirm,
                        width = 80,
                    )
                    styledButton(
                        text = "Cancel",
                        onClick = onCancel,
                        width = 80,
                    )
                }
            }
        }
    }
}