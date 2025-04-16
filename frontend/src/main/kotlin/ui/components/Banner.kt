package ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_banner
import org.jetbrains.compose.resources.painterResource

@Composable
fun Banner(
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier,
) {
    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = "Profile Banner",
            modifier = modifier
        )
    } else {
        Image(
            painter = painterResource(Res.drawable.default_banner),
            contentDescription = "Profile Banner",
            modifier = modifier
        )
    }
}