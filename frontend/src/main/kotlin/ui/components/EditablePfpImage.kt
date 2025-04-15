package ui.components

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_pfp
import org.jetbrains.compose.resources.painterResource

@Composable
private fun EditablePfpImage(
    imagePath: String,
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = rememberSaveable { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .hoverable(interactionSource = interactionSource, enabled = true)
    ) {
        if (imagePath.isNotEmpty()) {
            // Show image from path
            PfpImage(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .clip(CircleShape),
                imagePath = imagePath,
                onClick = onClick,
            )
        } else {
            // Show image vector
            PfpImage(
                modifier = Modifier.fillMaxSize()
                    .align(Alignment.Center)
                    .clip(CircleShape),
                imageBitmap = null,
                onClick = onClick,
            )
        }
        if (isHovered) {
            // Show hover effect
            Image(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxSize(0.25f)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun EditablePfpImage(
    imageBitmap: ImageBitmap?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    EditablePfpImage(
        imagePath = "",
        imageBitmap = imageBitmap,
        modifier = modifier,
        onClick = onClick,
    )

}
@Composable
fun EditablePfpImage(
    imagePath: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    EditablePfpImage(
        imagePath = imagePath,
        imageBitmap = null,
        modifier = modifier,
        onClick = onClick,
    )
}