package ui.views

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ui.components.AccountDetailField
import ui.theme.MainTheme

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
                        text = "LinkedInLite Login"
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
                                onClick = { onLogin() }
                            ) {
                                Text("Login")
                            }
                            Button(
                                modifier = Modifier.weight(1f),
                                onClick = { onRegister() },
                            ) {
                                Text("Don't have an account? Sign up here")
                            }
                        }
                    }
                )
            }
        ){
            // Main content of the login screen
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome to LinkedInLite! Please log in to continue.",
                    modifier = Modifier.padding(16.dp)
                )
                AccountDetailField(
                    label = "Email",
                    prompt = "Enter your email",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.padding(16.dp)
                )
                AccountDetailField(
                    label = "Password",
                    prompt = "Enter your password",
                    keyboardType = KeyboardType.Password,
                    password = true,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}