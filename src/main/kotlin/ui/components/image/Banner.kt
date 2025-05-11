package ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_banner
import org.jetbrains.compose.resources.imageResource

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
        contentScale = ContentScale.FillWidth,
        contentDescription = "Profile Banner",
        modifier = modifier
    )
}