package ui.components.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.graphics.ImageBitmap

/**
 * Creates an editable profile picture image. The image displays a hover effect
 * indicating that it can be clicked to change the profile picture. Clipped to
 * a circle shape and maintains a 1:1 aspect ratio.
 *
 * @param imagePath The path to the image file. If empty, attempts ot use imageBitmap.
 * @param imageBitmap The image bitmap to be displayed. If null, a default image is shown.
 * @param modifier The modifier for the image.
 * @param onClick The callback for when the image is clicked.
 */
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
                imageBitmap = imageBitmap,
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

/**
 * Creates an editable profile picture image. The image displays a hover effect
 * indicating that it can be clicked to change the profile picture. Clipped to
 * a circle shape and maintains a 1:1 aspect ratio.
 *
 * @param imageBitmap The image bitmap to be displayed. If null, a default image is shown.
 * @param modifier The modifier for the image.
 * @param onClick The callback for when the image is clicked.
 */
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

/**
 * Creates an editable profile picture image. The image displays a hover effect
 * indicating that it can be clicked to change the profile picture. Clipped to
 * a circle shape and maintains a 1:1 aspect ratio.
 *
 * @param imagePath The path to the image file. Displays a default image if empty.
 * @param modifier The modifier for the image.
 * @param onClick The callback for when the image is clicked.
 */
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