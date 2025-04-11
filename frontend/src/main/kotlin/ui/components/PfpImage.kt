package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.File

val pfpModifier = Modifier
    .fillMaxSize()

private fun getBitmapFromFilePath(imagePath: String): ImageBitmap? {
    val file = File(imagePath)
    var bitmap: ImageBitmap? = null

    if (file.exists()) {
        val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(file.readBytes())
        bitmap = skiaImage.toComposeImageBitmap()
    }

    return bitmap
}

@Composable
fun PfpImage(
    imageVector: ImageVector? = Icons.Default.AccountCircle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if (imageVector == null) {
        Image(
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Picture",
        )
    } else {
        Image(
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            imageVector = imageVector,
            contentDescription = "Profile Picture",
        )
    }
}

@Composable
fun PfpImage(
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val bitmap = getBitmapFromFilePath(imagePath)

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            contentDescription = "Profile Picture",
        )
    } else {
        PfpImage(
            modifier = modifier.then(pfpModifier),
            onClick = onClick
        )
    }
}