package ui.views.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.DataSource.tags
import ui.components.AccountDetailField
import ui.components.Field
import ui.components.styles.styledButton
import ui.components.styles.styledDropDownList
import ui.theme.LIGHT_PURPLE
import ui.views.registerorg.RegisterOrgInfoUiState

/**
 * The registration screen allowing the user to input information about his or her organization
 *
 * @param uiState The current state of the registration screen.
 * @param onContinue Callback function for when the user clicks the continue button.
 * @param onBack Callback function for when the user clicks the back button.
 */
@Composable
fun registerInfoScreen(
    uiState: RegisterInfoUiState,
    onContinue: () -> Unit,
    onBack: () -> Unit,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // Main content of the register screen
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(top = 32.dp))

            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Create an Account",
                color = MaterialTheme.colorScheme.onBackground,
                style = ui.theme.Typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "First Name",
                prompt = "Enter name",
                onTextChanged = { uiState.name = it },
                keyboardType = KeyboardType.Text,
                value = uiState.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Last Name",
                prompt = "Enter name",
                onTextChanged = { uiState.surname = it },
                keyboardType = KeyboardType.Text,
                value = uiState.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "School Name",
                prompt = "Enter school name",
                onTextChanged = { uiState.schoolName = it },
                keyboardType = KeyboardType.Text,
                value = uiState.schoolName,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            androidx.compose.material.Text(
                text = "Interests",
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            styledDropDownList(
                items = tags,
                modifier = Modifier,
                width = 256,
                multiSelect = true,
                noSelectionText = "Select Interests",
                value = uiState.tags.joinToString(", "),
                onSelect = {
                    val selectedTag = it
                    if (selectedTag in uiState.tags) {
                        uiState.tags.remove(selectedTag)
                    } else {
                        uiState.tags.add(selectedTag)
                    }
                }
            )

            Spacer(modifier = Modifier.padding(top = 64.dp))

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
    }
}