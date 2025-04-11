package ui.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.AccountDetailField
import ui.components.styledButton
import ui.theme.MainTheme
import ui.theme.LIGHT_PURPLE

@Composable
fun loginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    // Login screen UI
    MainTheme {
        Scaffold (
            topBar = {
                TopAppBar (
                    title = { Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "LinkedInLite Login",
                    ) }
                )
            },
            bottomBar = {
                BottomAppBar (
                    modifier = Modifier
                        .background(Color.LightGray),
                    content = {
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
                    }
                )
            }
        ){
            // Main content of the login screen
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.padding(top = 32.dp))

                AccountDetailField(
                    label = "Email",
                    prompt = "Enter your email",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(0.dp)
                )
                AccountDetailField(
                    label = "Password",
                    prompt = "Enter your password",
                    keyboardType = KeyboardType.Password,
                    password = true,
                    modifier = Modifier.padding(0.dp)
                )
            }
        }
    }
}