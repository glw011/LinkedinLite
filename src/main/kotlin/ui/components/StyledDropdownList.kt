/* -------------------------------------------------------------------------------------------------
Jayden Toussaint, Harrison Day - 04/11/25
Styled Dropdown List for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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

/**
 * Creates a styled dropdown list component.
 *
 * This composable provides a dropdown list with support for single or multiple selections.
 * It uses an `ExposedDropdownMenuBox` to display the dropdown and a `styledTextField`
 * to show the currently selected item(s).
 *
 * The dropdown can display a list of items, and optionally allow the user to select
 * multiple items from the list. The currently selected item (or items, in multi-select mode)
 * is displayed in a styled text field above the dropdown.
 *
 * @param items The list of strings to be displayed as options in the dropdown.
 * @param modifier Modifier to apply to the dropdown list layout.
 * @param width The width of the dropdown list. Defaults to 256.
 * @param multiSelect True if multiple selections are allowed, false for single selection. Defaults to false.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun styledDropDownList(
    items: List<String>,
    modifier: Modifier = Modifier,
    width: Int = 256,
    multiSelect: Boolean = false
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var dropDownIcon by rememberSaveable { mutableStateOf(Icons.Filled.KeyboardArrowDown) }
    var selectedText by rememberSaveable { mutableStateOf(items[0]) }
    val selectedItems = rememberSaveable { mutableStateListOf<String>() }

    if (isExpanded) dropDownIcon = Icons.Filled.KeyboardArrowUp
    else dropDownIcon = Icons.Filled.KeyboardArrowDown

    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
        ) {
            styledTextField(
                onTextChanged = { selectedText = it },
                unfocusedText = selectedItems.joinToString(", "),
                width = width,
                xAlignment = Alignment.CenterHorizontally,
                icon = dropDownIcon,
                iconAlignment = Alignment.CenterEnd,
                showIcon = true,
            )

            if (multiSelect) {
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }) {
                    items.forEachIndexed { index, text ->
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
                                Row(
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

            else {
                ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                    items.forEachIndexed{index, text ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = items[index]
                                isExpanded = false
                            },
                            content = {
                                Row (
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text)
                                }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}