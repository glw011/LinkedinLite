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
fun registerOrgScreen(onContinue: () -> Unit, onBack: () -> Unit) {
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
                AccountDetailField(
                    label = "Organization Name",
                    prompt = "Enter name",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth()
                )
                AccountDetailField(
                    label = "Email",
                    prompt = "Enter email",
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.fillMaxWidth()
                )
                AccountDetailField(
                    label = "Password",
                    prompt = "Enter password",
                    keyboardType = KeyboardType.Password,
                    password = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}