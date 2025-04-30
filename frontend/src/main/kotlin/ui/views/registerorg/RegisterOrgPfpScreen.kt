package ui.views.registerorg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.EditablePfpImage
import ui.components.styledButton
import ui.theme.LIGHT_PURPLE
import util.getBitmapFromFilepath
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Opens a file manager dialog on the user system to choose an image file.
 * Accepts jpg, png, and gif files.
 *
 * @return The absolute path of the selected image file as a String. If no file is selected,
 * returns an empty string.
 */
fun openFileChooser(): String {
    val fileChooser = JFileChooser()
    fileChooser.fileFilter =
        FileNameExtensionFilter("Image Files", "jpg", "png", "gif")
    val returnValue = fileChooser.showOpenDialog(null)

    val imagePath: String

    // Check if a file was selected
    if (returnValue == JFileChooser.APPROVE_OPTION) {
        val selectedFile = fileChooser.selectedFile
        imagePath = selectedFile.absolutePath
    } else {
        imagePath = ""
    }

    return imagePath
}

/**
 * The registration screen allowing the user to upload a profile picture for their organization.
 *
 * @param onContinue Callback function for when the user clicks the continue button.
 * @param onBack Callback function for when the user clicks the back button.
 */
@Composable
fun registerOrgPfpScreen(
    uiState: RegisterOrgPfpUiState,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
    var imagePath by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // Main content of the register screen
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 32.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Create an Account",
                color = MaterialTheme.colorScheme.onBackground,
                style = ui.theme.Typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 32.dp))

                EditablePfpImage(
                    imageBitmap = uiState.profilePicture.value,
                    modifier = Modifier.fillMaxHeight(0.3f),
                    onClick = {
                        imagePath = openFileChooser()
                        val bitmap = getBitmapFromFilepath(imagePath)
                        if (bitmap != null) {
                            uiState.profilePicture.value = bitmap
                        }
                    }
                )

                Text(
                    text = "Please upload a profile picture for your organization",
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(16.dp))

                Spacer(modifier = Modifier.padding(top = 32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    styledButton(
                        text = "Continue",
                        width = 80,
                        xAlignment = Alignment.CenterHorizontally,
                        onClick = { onContinue() },
                        buttonColor = LIGHT_PURPLE,
                        textColor = Color.White,
                    )

                    Spacer(modifier = Modifier.weight(0.05f))

                    styledButton(
                        text = "Back",
                        width = 80,
                        xAlignment = Alignment.CenterHorizontally,
                        onClick = { onBack() },
                        buttonColor = LIGHT_PURPLE,
                        textColor = Color.White,
                    )

                    Spacer(modifier = Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}