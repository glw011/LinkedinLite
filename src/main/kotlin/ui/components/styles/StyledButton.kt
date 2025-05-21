/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/11/25
Styled Button for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components.styles

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Creates a styled button with customizable properties.
 *
 * This composable provides a visually appealing button with options for:
 * - Setting a fixed [width].
 * - Customizing horizontal alignment ([xAlignment]).
 * - Displaying text inside the button ([text]).
 * - Defining click behavior ([onClick]).
 * - Changing the background color of the button ([buttonColor]).
 * - Changing the text color of the button ([textColor]).
 *
 * The button's appearance is styled with a rounded shape, and the colors
 * are customizable.
 *
 * @param width The width of the button in density-independent pixels (dp). Defaults to 256.
 * @param xAlignment The horizontal alignment of the button within its parent. Defaults to [Alignment.Start].
 * @param text The text to display inside the button. Defaults to an empty string.
 * @param onClick Lambda that will be executed when the button is clicked. Defaults to an empty lambda.
 * @param buttonColor The background color of the button. Defaults to [Color.LightGray].
 * @param textColor The color of the text inside the button. Defaults to [Color.Black].
 */
@Composable
fun styledButton(
    width: Int = 256,
    xAlignment: Alignment.Horizontal = Alignment.Start,
    text: String = "",
    onClick: () -> Unit = {},
    buttonColor: Color = Color.LightGray,
    textColor: Color = Color.Black,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = modifier
            .width(width.dp)
            .height(32.dp)
            .background(buttonColor, RoundedCornerShape(32.dp))
            .border(0.dp, Color.Transparent, RoundedCornerShape(32.dp))
            .clickable(onClick = onClick)
            .padding(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp
        )
    }
}