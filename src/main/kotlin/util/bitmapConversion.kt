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

    try {
        val outFile = File("buffImg.png")
        ImageIO.write(bufferedImage, "png", outFile)
        println("BufferedImage saved to ${outFile.absolutePath}")
    } catch (e: Exception) {
        println("Failed to save BufferedImage: ${e.message}")
        e.printStackTrace()
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

    try {
        val skiaImage = Image.makeFromBitmap(bitmap)
        val data = skiaImage.encodeToData(EncodedImageFormat.PNG)
        if (data != null) {
            val outFile = File("converted_back.png")
            FileOutputStream(outFile).use { it.write(data.bytes) }
            println("Skia Image saved to ${outFile.absolutePath}")
        } else {
            println("Skia image encoding returned null")
        }
    } catch (e: Exception) {
        println("Failed to save Skia image: ${e.message}")
        e.printStackTrace()
    }

    return Image.makeFromBitmap(bitmap).toComposeImageBitmap()
}
