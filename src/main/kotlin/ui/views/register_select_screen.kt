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
fun registerSelectScreen(onPerson: () -> Unit, onOrg: () -> Unit,  onBack: () -> Unit) {
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
                        Button(
                            modifier = Modifier.fillMaxWidth(0.3f),
                            onClick = { onBack() }
                        ) {
                            Text("Already have an account? Sign in")
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
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Select an account type",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.weight(0.75f))
                    Button(
                        modifier = Modifier.weight(1f).padding(8.dp).fillMaxWidth(0.3f),
                        onClick = { onPerson() }
                    ) {
                        Text("Personal Account")
                    }
                    Button(
                        modifier = Modifier.weight(1f).padding(8.dp).fillMaxWidth(0.3f),
                        onClick = { onOrg() },
                    ) {
                        Text("Organization Account")
                    }
                    Spacer(modifier = Modifier.weight(1.5f))
                }
            }
        }
    }
}