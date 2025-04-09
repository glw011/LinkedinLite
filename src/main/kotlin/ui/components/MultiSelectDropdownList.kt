package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun multiDropdownList(items: List<String>, modifier: Modifier = Modifier) {

    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val selectedItems = rememberSaveable { mutableStateListOf<String>() }

    Column (
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            TextField(
                value = selectedItems.joinToString(", "),
                onValueChange = { },
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = isExpanded
                    )
                },
            )
            ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                items.forEachIndexed{index, text ->
                    DropdownMenuItem(
                        onClick = {
                            if (text in selectedItems) {
                                selectedItems.remove(text)
                            } else {
                                selectedItems.add(text)
                            }
                            isExpanded = false
                        },
                        content = {
                            Row (
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = text,
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(end = 16.dp)
                                )
                                if (text in selectedItems) {
                                    Icon(
                                        Icons.Rounded.Check,
                                        contentDescription = "Selected"
                                    )
                                }
                            }
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}