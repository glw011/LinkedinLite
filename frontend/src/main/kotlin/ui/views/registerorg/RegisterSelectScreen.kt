package ui.views.registerorg

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.styles.styledButton
import ui.theme.LIGHT_PURPLE

/**
 * The registration screen allowing the user to select between a personal or organization account.
 *
 * @param onPerson Callback function for when the user selects a personal account.
 * @param onOrg Callback function for when the user selects an organization account.
 */
@Composable
fun registerSelectScreen(onPerson: () -> Unit, onOrg: () -> Unit,  onBack: () -> Unit) {
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