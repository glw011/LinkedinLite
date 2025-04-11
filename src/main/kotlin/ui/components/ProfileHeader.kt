package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import ui.views.openFileChooser

@Composable
fun ProfileHeader(
    name: String,
    description: String,
    entity: String,
    banner: ImageBitmap,
) {
    var imagePath by rememberSaveable { mutableStateOf("") }
    Box(

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Image(
                bitmap = banner,
                contentDescription = "Banner",
                modifier = Modifier.weight(1f)
            )


        }
        EditablePfpImage(
            imagePath = imagePath, // TODO: Get image path from state,
            modifier = Modifier,
            onClick = { imagePath = openFileChooser() }
        )
    }
}