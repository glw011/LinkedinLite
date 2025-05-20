package ui.views.register.credentials

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.AccountDetailField
import ui.components.styles.styledButton
import ui.theme.LIGHT_PURPLE

/**
 * UI state for the RegisterCredentials screen.
 *
 * @param onContinue Callback function to be called when the user clicks "Continue".
 * @param onBack Callback function to be called when the user clicks "Back".
 */
@Composable
fun registerCredentialsScreen(
    uiState: RegisterCredentialsUiState,
    onEvent: (RegisterCredentialsEvent) -> Unit,
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

            AccountDetailField(
                label = "Email",
                prompt = "Enter email",
                keyboardType = KeyboardType.Email,
                onTextChanged = { onEvent(RegisterCredentialsEvent.EmailChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Password",
                prompt = "Enter password",
                keyboardType = KeyboardType.Password,
                onTextChanged = { onEvent(RegisterCredentialsEvent.PasswordChanged(it)) },
                password = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Confirm Password",
                prompt = "Re-enter password",
                keyboardType = KeyboardType.Password,
                onTextChanged = { onEvent(RegisterCredentialsEvent.ConfirmPasswordChanged(it)) },
                password = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(0.15f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(1f))

                styledButton(
                    text = "Continue",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = {
                        onEvent(RegisterCredentialsEvent.OnContinue)
                    },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.weight(0.05f))

                styledButton(
                    text = "Back",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = {
                        onEvent(RegisterCredentialsEvent.OnBack)
                    },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.weight(0.05f))

            Text(
                text = uiState.errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = ui.theme.Typography.bodySmall
            )

            Spacer(modifier = Modifier.weight(0.3f))
        }
    }
}