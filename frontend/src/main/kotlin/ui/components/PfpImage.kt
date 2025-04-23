package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource
import java.io.File

val pfpModifier = Modifier
    .fillMaxSize()

/**
 * Retrieves an image from a file path and converts it to an ImageBitmap.
 *
 * @param imagePath The file path of the image.
 * @return The ImageBitmap representation of the image, or null if the file does not exist.
 */
fun getBitmapFromFilePath(imagePath: String): ImageBitmap? {
    val file = File(imagePath)
    var bitmap: ImageBitmap? = null

    if (file.exists()) {
        val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(file.readBytes())
        bitmap = skiaImage.toComposeImageBitmap()
    }

    return bitmap
}

/**
 * Creates a profile picture image. Displays a default image if no image is provided.
 *
 * @param imageBitmap The image bitmap to be displayed. Nullable.
 * @param contentScale The scaling rules for the image.
 * @param modifier The modifier for the image.
 * @param onClick The callback for when the image is clicked.
 */
@Composable
fun PfpImage(
    imageBitmap: ImageBitmap?,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if (imageBitmap == null) {
        Image(
            contentScale = contentScale,
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            painter = painterResource(Res.drawable.default_pfp),
            contentDescription = "Profile Picture",
        )
    } else {
        Image(
            contentScale = contentScale,
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            bitmap = imageBitmap,
            contentDescription = "Profile Picture",
        )
    }
}

/**
 * Creates a profile picture image. Displays a default image if no image is provided.
 *
 * @param imagePath The path to the image file. Displays a default image if empty.
 * @param modifier The modifier for the image.
 * @param onClick The callback for when the image is clicked.
 */
@Composable
fun PfpImage(
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val bitmap = getBitmapFromFilePath(imagePath)

    PfpImage(
        imageBitmap = bitmap,
        modifier = modifier.then(pfpModifier),
        onClick = onClick
    )
}