package utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Object to hold screen dimensions
object ScreenDimensions {
    var width: Dp = 0.dp
    var height: Dp = 0.dp
}

// Function that calculates the screen dimensions
@Composable
fun updateScreenDimensions() {
    // helper Box to measure the screen size
    Box(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged { size ->
                ScreenDimensions.height = size.height.dp
                ScreenDimensions.width = size.width.dp
            }
    )
}
