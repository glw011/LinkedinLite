package ui.components.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale

@Composable
fun Image(
    bitmap: ImageBitmap?,
    defaultImage: ImageBitmap,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center,
    modifier: Modifier = Modifier
) {
    val image: ImageBitmap
    if (bitmap == null) {
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