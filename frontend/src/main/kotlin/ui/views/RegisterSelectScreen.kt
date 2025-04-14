package ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.styledButton
import ui.theme.LIGHT_PURPLE
import ui.theme.MainTheme

@Composable
fun registerSelectScreen(onPerson: () -> Unit, onOrg: () -> Unit,  onBack: () -> Unit) {
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
            )
        }
    ) {
        // Main content of the register screen
        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 32.dp))

            Text(
                text = "Select an account type",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
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
        }
    }
}