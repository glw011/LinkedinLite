package ui.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.AccountDetailField
import ui.components.styledButton
import ui.theme.LIGHT_PURPLE

/**
 * The login screen. Displays upon app launch if no user account is detected.
 * Allows the user to log in with a an email or password or register a new account.
 *
 * @param onLogin Callback function for when the user clicks the login button.
 * @param onRegister Callback function for when the user clicks the register button.
 */
@Composable
fun loginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    // Login screen UI
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
                text = "LinkedInLite Login",
                color = MaterialTheme.colorScheme.onBackground,
                style = ui.theme.Typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Email",
                prompt = "Enter your email",
                keyboardType = KeyboardType.Email,
                modifier = Modifier.padding(0.dp)
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Password",
                prompt = "Enter your password",
                keyboardType = KeyboardType.Password,
                password = true,
                modifier = Modifier.padding(0.dp)
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                styledButton(
                    text = "Login",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onLogin() },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.weight(0.05f))

                styledButton(
                    text = "Register",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onRegister() },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}