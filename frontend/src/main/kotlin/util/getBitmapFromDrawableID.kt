package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.compose.resources.DrawableResource
import java.io.File

fun getBitmapFromDrawableID(resourceID: DrawableResource): ImageBitmap? {
    val file = File(resourceID.toString())
    var bitmap: ImageBitmap? = null

    if (file.exists()) {
        val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(file.readBytes())
        bitmap = skiaImage.toComposeImageBitmap()
    }

    return bitmap
}