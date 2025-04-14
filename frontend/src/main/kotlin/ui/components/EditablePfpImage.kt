package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
private fun EditablePfpImage(
    imagePath: String,
    imageVector: ImageVector?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val interactionSource = rememberSaveable { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Box(
        modifier = modifier
            .hoverable(interactionSource = interactionSource, enabled = true)
            .clip(CircleShape)
    ) {
        if (imagePath.isNotEmpty()) {
            // Show image from path
            PfpImage(
                modifier = Modifier,
                imagePath = imagePath,
                onClick = onClick,
            )
        } else {
            // Show image vector
            PfpImage(
                modifier = Modifier,
                imageVector = imageVector,
                onClick = onClick,
            )
        }
        if (isHovered) {
            // Show hover effect
            Image(
                imageVector = Icons.Default.AddCircle,
                contentDescription = "Profile Picture",
                modifier = modifier
                    .fillMaxSize(0.25f)
                    .clip(CircleShape)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun EditablePfpImage(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    EditablePfpImage(
        imagePath = "",
        imageVector = imageVector,
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
        imageVector = null,
        modifier = modifier,
        onClick = onClick,
    )
}