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
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource
import java.io.File

val pfpModifier = Modifier
    .fillMaxSize()

fun getBitmapFromFilePath(imagePath: String): ImageBitmap? {
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
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if (imageBitmap == null) {
        Image(
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            painter = painterResource(Res.drawable.default_pfp),
            contentDescription = "Profile Picture",
        )
    } else {
        Image(
            modifier = modifier.then(pfpModifier).clickable(onClick = onClick),
            bitmap = imageBitmap,
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

    PfpImage(
        imageBitmap = bitmap,
        modifier = modifier.then(pfpModifier),
        onClick = onClick
    )
}