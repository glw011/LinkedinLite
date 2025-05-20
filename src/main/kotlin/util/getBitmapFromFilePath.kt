package util

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import java.io.File

/**
 * Retrieves an image from a file path and converts it to an ImageBitmap.
 *
 * @param imagePath The file path of the image.
 * @return The ImageBitmap representation of the image, or null if the file does not exist.
 */
fun getBitmapFromFilepath(imagePath: String): ImageBitmap {
    val file = File(imagePath)
    var bitmap: ImageBitmap = ImageBitmap(0, 0)

    if (file.exists()) {
        val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(file.readBytes())
        bitmap = skiaImage.toComposeImageBitmap()
    }

    return bitmap
}