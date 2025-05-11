package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.example.linkedinlite.generated.resources.Res
import org.example.linkedinlite.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource

@Composable
fun userPfp(userName: String, size: Int = 32) {
    // Grab user pfp from database

    // If pfp not found, use default pfp
    Image(
        painter = painterResource(Res.drawable.default_pfp),
        contentDescription = "Default Profile Picture",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}