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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.DataSource.tags
import ui.components.AccountDetailField
import ui.components.dropdownList
import ui.components.multiDropdownList
import ui.components.pfpModifier
import ui.components.styledButton
import ui.components.styledDropDownList
import ui.theme.LIGHT_PURPLE
import ui.theme.MainTheme

@Composable
fun registerOrgInfoScreen(onContinue: () -> Unit, onBack: () -> Unit) {
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
                            verticalAlignment = Alignment.CenterVertically,
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
                )
            }
        ) {
            // Main content of the register screen
            Column (
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(top = 32.dp))

                AccountDetailField(
                    label = "School Name",
                    prompt = "Enter school name",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(top = 32.dp))

                AccountDetailField(
                    label = "Organization Name",
                    prompt = "Enter name",
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(top = 32.dp))

                Text(
                    text="Organization Type",
                )
                styledDropDownList(
                    items = tags,
                    modifier = Modifier.fillMaxWidth(),
                    width = 256,
                    multiSelect = true
                )
            }
        }
    }
}