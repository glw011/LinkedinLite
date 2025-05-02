package ui.components.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.imageResource
import util.getBitmapFromFilepath

var pfpModifier = Modifier
    .fillMaxSize()

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
    isClickable: Boolean = true,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Image(
        bitmap = imageBitmap,
        defaultImage = imageResource(Res.drawable.default_pfp),
        contentScale = contentScale,
        contentDescription = "Profile Picture",
        modifier = modifier.then(pfpModifier).clickable(onClick = onClick)
    )
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
    val bitmap = getBitmapFromFilepath(imagePath)

    PfpImage(
        imageBitmap = bitmap,
        modifier = modifier.then(pfpModifier),
        onClick = onClick
    )
}