package ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.AccountDetailField
import ui.components.styledButton
import ui.theme.DARK_MODE
import ui.theme.LIGHT_PURPLE
import ui.theme.MainTheme

@Composable
fun registerSelectScreen(onPerson: () -> Unit, onOrg: () -> Unit,  onBack: () -> Unit) {
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

                styledButton(
                    text = "Personal",
                    width = 128,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onPerson() },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.padding(top = 32.dp))

                styledButton(
                    text = "Organization",
                    width = 128,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onOrg() },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.padding(top = 32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))

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

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}