/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/11/25
Styled Text Field for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Creates a styled text field with customizable properties.
 *
 * This composable provides a visually appealing text field with options for:
 * - Handling text input and changes via [onTextChanged].
 * - Setting a fixed [width].
 * - Customizing horizontal alignment ([xAlignment]).
 * - Displaying placeholder text when the field is unfocused and empty ([unfocusedText]).
 * - Including an optional icon ([icon], [showIcon], [iconAlignment]).
 * - Enabling or disabling password input mode ([isPassword]).
 * - Defining click behavior ([onClick]).
 * - Enabling or disabling typing ([canType]).
 * - Switching between single-line and multi-line input ([singleLine]).
 *
 * The text field's appearance is styled with a light gray background, rounded corners,
 * and dark gray text.
 *
 * @param onTextChanged Lambda that will be executed when the text changes. It receives the new text as a parameter.
 * @param width The width of the text field in density-independent pixels (dp). Defaults to 256.
 * @param xAlignment The horizontal alignment of the text field within its parent. Defaults to [Alignment.Start].
 * @param unfocusedText The text to display when the field is unfocused and empty. Defaults to an empty string.
 * @param icon The icon to display in the text field. Defaults to the search icon ([Icons.Filled.Search]).
 * @param iconAlignment The alignment of the icon within the text field. Defaults to [Alignment.CenterStart].
 * @param showIcon Whether to display the icon. Defaults to `false`.
 * @param isPassword Whether the text field is for password input. This will obscure the entered text. Defaults to `false`.
 * @param modifier Modifier to apply to the text field.
 * @param onClick Lambda that will be executed when the text field is clicked. Defaults to an empty lambda.
 * @param canType Whether the text field is enabled for typing. If `false`, the field will be read-only. Defaults to `true`.
 * @param singleLine Whether the text field should be a single line or allow multiple lines. Defaults to `true`.
 */
@Composable
fun styledTextField(
    onTextChanged: (String) -> Unit,
    width: Int = 256,
    xAlignment: Alignment.Horizontal = Alignment.Start,
    unfocusedText: String = "",
    icon: androidx.compose.ui.graphics.vector.ImageVector = Icons.Filled.Search,
    iconAlignment: Alignment = Alignment.CenterStart,
    showIcon: Boolean = false,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    canType: Boolean = true,
    singleLine: Boolean = true,
    fillMaxWidth: Boolean = false
) {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    var startPadding = 0.dp
    var endPadding = 0.dp

    if (showIcon) {
        if (iconAlignment == Alignment.CenterStart) {
            startPadding = 36.dp
            endPadding = 16.dp
        }
        else {
            startPadding = 16.dp
            endPadding = 36.dp
        }
    }
    else {
        startPadding = 16.dp
        endPadding = 16.dp
    }

    // Text Field
    Box(
        modifier = Modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier.width(width.dp))
            .height(32.dp)
            .background(Color.LightGray, RoundedCornerShape(32.dp))
            .border(0.dp, Color.Transparent, RoundedCornerShape(32.dp))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        // Displayed Text (determined by focus)
        if (text.isEmpty() && !isFocused) {
            Text(
                text = unfocusedText,
                color = DarkGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = startPadding, end = endPadding, bottom = 4.dp)
            )
        }

        // Text Box
        BasicTextField(
            value = text,
            onValueChange = {
                if (canType) {
                    text = it
                    onTextChanged(it)
                }
            },
            textStyle = TextStyle(fontSize = 12.sp, color = DarkGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startPadding, end = endPadding, bottom = 2.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = singleLine,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
            enabled = canType
        )

        if (showIcon) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .align(iconAlignment),
                tint = Color.DarkGray
            )
        }
    }
}