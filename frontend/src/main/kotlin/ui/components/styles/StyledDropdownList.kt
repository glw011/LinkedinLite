/* -------------------------------------------------------------------------------------------------
Jayden Toussaint, Harrison Day - 04/11/25
Styled Dropdown List for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components.styles

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.views.home.SEARCH_FILTERS

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
 * @param xAlignment The horizontal alignment of the dropdown list. Defaults to aenter alignment.
 * @param multiSelect True if multiple selections are allowed, false for single selection. Defaults to false.
 * @param noSelectionText The text to display when no items are selected. Defaults to an empty string.
 * @param value The currently selected value. Defaults to an empty string.
 * @param itemsSelected The list of currently selected items. Defaults to an empty list.
 * @param onSelect Callback function to be called when an item is selected. It receives the selected item as a parameter.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun styledDropDownList(
    items: List<String>,
    modifier: Modifier = Modifier,
    width: Int = 256,
    xAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    multiSelect: Boolean = false,
    noSelectionText: String = "",
    value: String = "",
    itemsSelected: List<String> = listOf(),
    onSelect: (String) -> Unit = {},
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var dropDownIcon by rememberSaveable { mutableStateOf(Icons.Filled.KeyboardArrowDown) }
    var selectedText: String by rememberSaveable { mutableStateOf(value) }
    val selectedItems = rememberSaveable { mutableStateListOf(*itemsSelected.toTypedArray()) }

    SEARCH_FILTERS = selectedItems

    dropDownIcon = Icons.Filled.KeyboardArrowDown
    if (isExpanded) dropDownIcon = Icons.Filled.KeyboardArrowUp

    if (selectedItems.isEmpty()) {
        selectedText = noSelectionText
    }
    else if (multiSelect && selectedItems.isNotEmpty()) {
        selectedText = selectedItems.joinToString(", ")
    } else if (!multiSelect && selectedItems.isNotEmpty()) {
        selectedText = selectedItems[0]
    }

    MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded },
            modifier = modifier,
        ) {
            styledTextField(
                onTextChanged = { selectedText = it },
                unfocusedText = selectedText,
                width = width,
                xAlignment = Alignment.CenterHorizontally,
                icon = dropDownIcon,
                iconAlignment = Alignment.CenterEnd,
                showIcon = true,
            )

            if (multiSelect) {
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier
                        .width(width.dp)
                        .background(
                            color = Color.LightGray,
                        )
                ) {
                    items.forEachIndexed { _, text ->
                        DropdownMenuItem(
                            onClick = {
                                onSelect(text)
                                if (text in selectedItems) {
                                    selectedItems.remove(text)
                                } else {
                                    selectedItems.add(text)
                                }
                            },
                            content = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = text,
                                        color = Color.DarkGray,
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .padding(end = 16.dp)
                                    )
                                    if (text in selectedItems) {
                                        Icon(
                                            Icons.Rounded.Check,
                                            contentDescription = "Selected",
                                            tint = Color.DarkGray // Light Gray checkmarks
                                        )
                                    }
                                }
                            },
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            } else {
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier
                        .width(width.dp)
                        .background(
                            color = Color.LightGray,
                        )
                ) {
                    items.forEachIndexed { index, text ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = items[index]
                            },
                            content = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = text,
                                        color = Color.DarkGray,
                                        modifier = Modifier
                                            .fillMaxWidth(0.9f)
                                            .padding(end = 16.dp)
                                    )
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