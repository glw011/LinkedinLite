/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/11/25
Styled Text Field for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components.styles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.vector.ImageVector
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
    height: Int = 32,
    xAlignment: Alignment.Horizontal = Alignment.Start,
    unfocusedText: String = "",
    icon: ImageVector = Icons.Filled.Search,
    iconAlignment: Alignment = Alignment.CenterStart,
    showIcon: Boolean = false,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    canType: Boolean = true,
    singleLine: Boolean = true,
    fillMaxWidth: Boolean = false,
    iconClickedFunctionPtr: () -> Unit = {},
    value: String = "",
    charLimit: Int = Int.MAX_VALUE,
) {
    var text by remember { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val startPadding = if (showIcon && iconAlignment == Alignment.CenterStart) 36.dp else 16.dp
    val endPadding = if (showIcon && iconAlignment != Alignment.CenterStart) 36.dp else 16.dp

    Box(
        modifier = modifier
            .then(if (fillMaxWidth) Modifier.fillMaxWidth() else Modifier.width(width.dp))
            .height(height.dp)
            .background(Color.LightGray, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        contentAlignment = if (height > 32) Alignment.TopStart else Alignment.CenterStart
    ) {
        if (text.isEmpty() && !isFocused) {
            Text(
                text = unfocusedText,
                color = DarkGray,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = startPadding, end = endPadding, top = if (height > 32) 8.dp else 0.dp)
            )
        }

        BasicTextField(
            value = text,
            onValueChange = {
                if (canType && it.length <= charLimit) {
                    text = it
                    onTextChanged(it)
                }
            },
            textStyle = TextStyle(fontSize = 12.sp, color = DarkGray),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = startPadding, end = endPadding, top = if (height > 32) 8.dp else 0.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                },
            singleLine = singleLine && height <= 32,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = if (singleLine) ImeAction.Search else ImeAction.Default
            ),
            enabled = canType
        )

        if (showIcon) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .align(iconAlignment)
                    .clickable { iconClickedFunctionPtr() },
                tint = DarkGray
            )
        }
    }
}