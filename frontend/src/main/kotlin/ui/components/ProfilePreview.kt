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
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource

/**
 * Composable function for displaying a preview of a profile.
 *
 * @param pfp The profile picture as an ImageBitmap.
 * @param name The name of the profile.
 * @param bio A short bio or description of the profile.
 * @param modifier Modifier for the profile preview.
 */
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
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(text = bio, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}