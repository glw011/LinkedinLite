package ui.views.register.pfp

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.image.EditablePfpImage
import ui.components.styles.styledButton
import ui.theme.LIGHT_PURPLE
import util.getBitmapFromFilepath
import util.openFileChooser

@Composable
fun registerPfpScreen(
    uiState: RegisterPfpUIState,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {
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
                    imageBitmap = uiState.profilePicture,
                    modifier = Modifier.fillMaxHeight(0.3f),
                    onClick = {
                        uiState.profilePicture = getBitmapFromFilepath(openFileChooser())
                    }
                )

                Text(
                    text = uiState.prompt,
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