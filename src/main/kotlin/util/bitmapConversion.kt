import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.toPixelMap
import org.jetbrains.skia.*
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.imageio.ImageIO

fun imageBitmapToBufferedImage(imageBitmap: ImageBitmap?): BufferedImage? {
    if (imageBitmap == null) return null

    val pixelMap = imageBitmap.toPixelMap()
    val width = pixelMap.width
    val height = pixelMap.height

    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

    for (y in 0 until height) {
        for (x in 0 until width) {
            val color = pixelMap[x, y].toArgb()
            bufferedImage.setRGB(x, y, color)
        }
    }

    return bufferedImage
}

fun bufferedImageToImageBitmap(bufferedImage: BufferedImage): ImageBitmap {
    val width = bufferedImage.width
    val height = bufferedImage.height

    val intPixels = IntArray(width * height)
    bufferedImage.getRGB(0, 0, width, height, intPixels, 0, width)

    val byteBuffer = ByteBuffer
        .allocate(intPixels.size * 4)
        .order(ByteOrder.nativeOrder())

    intPixels.forEach { pixel ->
        byteBuffer.putInt(pixel)
    }

    val bitmap = Bitmap().apply {
        val info = ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL)
        allocPixels(info)
        installPixels(byteBuffer.array())
    }

    return Image.makeFromBitmap(bitmap).toComposeImageBitmap()
}
