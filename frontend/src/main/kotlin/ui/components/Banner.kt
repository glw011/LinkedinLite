package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_banner
import org.jetbrains.compose.resources.imageResource
import ui.components.Image

/**
 * Creates a profile banner image.
 *
 * @param imageBitmap The image bitmap to be displayed as the banner. Nullable.
 * @param modifier The modifier for the image.
 */
@Composable
fun Banner(
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier,
) {
    Image(
        bitmap = imageBitmap,
        defaultImage = imageResource(Res.drawable.default_banner),
        contentDescription = "Profile Banner",
        modifier = modifier
    )
}