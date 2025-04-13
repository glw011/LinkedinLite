package ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import ui.theme.DARK_MODE
import ui.theme.LIGHT_PURPLE
import ui.theme.MainTheme
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

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

@Composable
fun registerOrgPfpScreen(onContinue: () -> Unit, onBack: () -> Unit) {
    var imagePath by remember { mutableStateOf("") }

    MainTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = if (DARK_MODE) ui.theme.backgroundDark else ui.theme.backgroundLight) {
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
                    style = ui.theme.Typography.bodyLarge
                )

                Spacer(modifier = Modifier.padding(top = 32.dp))

                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.padding(top = 32.dp))

                    val pfpOnClick = {
                        imagePath = openFileChooser()
                    }
                    val pfpModifier = Modifier
                        .fillMaxWidth(0.3f)
                        .fillMaxHeight(0.5f)
                    if (imagePath.isEmpty()) {
                        EditablePfpImage(
                            imageVector = Icons.Default.AccountCircle,
                            modifier = pfpModifier,
                            onClick = pfpOnClick
                        )
                    } else {
                        EditablePfpImage(
                            imagePath = imagePath,
                            modifier = pfpModifier,
                            onClick = pfpOnClick
                        )
                    }
                    Text("Please upload a profile picture for your organization", modifier = Modifier.padding(16.dp))

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
}