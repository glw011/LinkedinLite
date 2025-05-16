package ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale

/**
 * Creates an image composable. Allows for a nullable bitmap to be passed in.
 *
 * @param bitmap The image bitmap to be displayed. Nullable.
 * @param defaultImage The default image bitmap to be displayed if the provided bitmap is null.
 * @param contentDescription The content description for accessibility.
 * @param contentScale The scale type for the image.
 * @param alignment The alignment of the image within its bounds.
 * @param modifier The modifier for the image.
 */
@Composable
fun Image(
    bitmap: ImageBitmap,
    defaultImage: ImageBitmap,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    modifier: Modifier = Modifier
) {
    val image: ImageBitmap
    if (bitmap.height == 0 || bitmap.width == 0) {
        image = defaultImage
    } else {
        image = bitmap
    }

    androidx.compose.foundation.Image(
        bitmap = image,
        contentScale = contentScale,
        contentDescription = contentDescription,
        alignment = alignment,
        modifier = modifier
    )
}