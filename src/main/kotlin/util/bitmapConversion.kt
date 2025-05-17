import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toPixelMap
import java.awt.image.BufferedImage
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.*
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun imageBitmapToBufferedImage(imageBitmap: ImageBitmap?): BufferedImage? {
    if (imageBitmap == null) return null

    val pixelMap = imageBitmap.toPixelMap()
    val bufferedImage = BufferedImage(pixelMap.width, pixelMap.height, BufferedImage.TYPE_INT_ARGB)

    for (y in 0 until pixelMap.height) {
        for (x in 0 until pixelMap.width) {
            val color = pixelMap[x, y] // ARGB format
            bufferedImage.setRGB(x, y, color.toArgb())
        }
    }

    return bufferedImage
}

fun bufferedImageToImageBitmap(bufferedImage: BufferedImage): ImageBitmap {
    val width = bufferedImage.width
    val height = bufferedImage.height

    // Get ARGB pixel data from BufferedImage
    val intPixels = IntArray(width * height)
    bufferedImage.getRGB(0, 0, width, height, intPixels, 0, width)

    // Convert IntArray to ByteArray (little endian = BGRA layout)
    val byteBuffer = ByteBuffer
        .allocate(intPixels.size * 4)
        .order(ByteOrder.nativeOrder())

    intPixels.forEach { pixel ->
        byteBuffer.putInt(pixel)
    }

    val byteArray = byteBuffer.array()

    // Create Skia bitmap and install pixels
    val info = ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL)
    val bitmap = Bitmap()
    bitmap.allocPixels(info)
    bitmap.installPixels(byteArray)

    return Image.makeFromBitmap(bitmap).toComposeImageBitmap()
}