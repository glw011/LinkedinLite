package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import org.jetbrains.compose.resources.painterResource

/**
 * Extension function for String that loads an ImageBitmap from a file path.
 *
 * @return The ImageBitmap if the file is loaded successfully, otherwise null.
 */
fun String.toImageBitmap(): ImageBitmap? {
    val file = File(this) // 'this' refers to the string itself (the file path)
    return if (file.exists()) {
        try {
            FileInputStream(file).use { inputStream ->
                loadImageBitmap(inputStream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    } else {
        null
    }
}

/**
 * Composable function for displaying a preview of a profile.
 *
 * @param pfp The profile picture as an ImageBitmap.
 * @param name The name of the profile.
 * @param bio A short bio or description of the profile.
 * @param modifier Modifier for the profile preview.
 */
@OptIn(ExperimentalResourceApi::class)
@Composable
fun profilePreview(pfp: ImageBitmap?, name: String, bio: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Profile Picture
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            if (pfp != null) {
                Image(
                    painter = BitmapPainter(pfp),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )
            }

            else {
                Image(
                    painter = painterResource(Res.drawable.default_pfp),
                    contentDescription = "Default Profile Picture",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(CircleShape)
                )
            }
        }

        // Spacer
        Spacer(modifier = Modifier.width(16.dp))

        // Name and Bio (in a column)
        Column {
            Text(text = name, fontWeight = FontWeight.Bold)
            Text(text = bio)
        }
    }
}