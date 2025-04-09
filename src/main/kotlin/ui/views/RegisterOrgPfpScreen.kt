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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.EditablePfpImage
import ui.theme.MainTheme
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

private fun openFileChooser(): String {
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
        Scaffold (
            topBar = {
                TopAppBar (
                    title = { Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Create an Account"
                    ) }
                )
            },
            bottomBar = {
                BottomAppBar (
                    content = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Button(
                                modifier = Modifier.weight(1f).padding(64.dp, 0.dp),
                                onClick = { onContinue() }
                            ) {
                                Text("Continue")
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = { onBack() },
                            ) {
                                Text("Back")
                            }
                        }
                    }
                )
            }
        ) {
            // Main content of the register screen
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
            }
        }
    }
}