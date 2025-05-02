package ui.components.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import data.DataSource
import ui.components.styles.styledButton
import ui.components.styles.styledDropDownList

@Composable
fun ProfileTagsEditDialog(
    tags: List<String>,
    onSelect: (String) -> Unit,
    onSave: () -> Unit,
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Edit Tags",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.CenterHorizontally),
                )

                styledDropDownList(
                    items = DataSource.tags,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    multiSelect = true,
                    noSelectionText = "Select Organization Tags",
                    value = tags.joinToString(", "),
                    itemsSelected = tags,
                    onSelect = onSelect,
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    styledButton(
                        text = "Save",
                        width = 80,
                        onClick = onSave,
                    )
                    styledButton(
                        text = "Cancel",
                        width = 80,
                        onClick = onCancel,
                    )
                }
            }
        }
    }
}