/* -------------------------------------------------------------------------------------------------
Harrison Day - 04/04/25
Sidebar file for LinkedInLite
------------------------------------------------------------------------------------------------- */

package ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.views.SEARCH_BAR_TEXT

/**
 * Composable function for the search bar UI element.
 *
 * This function renders a search bar that slides in from the top of the screen when
 * activated and slides out when deactivated. It includes text input, placeholder text,
 * a search icon, and handles focus and user input.
 *
 * @param onSearchTextChanged Lambda function to call when the search text changes.
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun searchBar(onSearchTextChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenHeight = remember(windowInfo) {
        with(density) { windowInfo.containerSize.height }
    }

    LaunchedEffect(searchText) {
        SEARCH_BAR_TEXT = searchText
        printSearchBarText()
    }

    AnimatedVisibility(
        visible = searchActive,
        enter = slideInVertically(
            animationSpec = tween(durationMillis = 250),
            initialOffsetY = { -screenHeight }
        ),
        exit = slideOutVertically(
            animationSpec = tween(durationMillis = 250),
            targetOffsetY = { -screenHeight }
        )
    ) {
        // Search Bar
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        focusManager.clearFocus()
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Color.LightGray, RoundedCornerShape(32.dp))
                    .border(0.dp, Color.Transparent, RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.CenterStart
            ) {
                // Displayed Text (determined by focus)
                if (searchText.isEmpty() && !isFocused) {
                    Text(
                        text = "Search People / Organizations",
                        color = DarkGray,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 36.dp, bottom = 4.dp)
                    )
                }

                // Text Box
                BasicTextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    textStyle = TextStyle(fontSize = 12.sp, color = DarkGray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 16.dp, bottom = 2.dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search)
                )

                // Search Icon
                Icon(
                    Icons.Filled.Search,
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(Alignment.CenterStart)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Temporary function to print the current search bar text to the console.
 *
 * This function is used for debugging purposes to print the current text
 * in the search bar. It will be removed or refactored in the future.
 */
// Temporary
fun printSearchBarText(){
    println(SEARCH_BAR_TEXT)
}