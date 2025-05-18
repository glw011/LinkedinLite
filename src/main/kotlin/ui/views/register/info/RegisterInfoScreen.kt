package ui.views.register.info

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
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import data.AccountType
import model.ModelManager
import ui.components.AccountDetailField
import ui.components.styles.styledButton
import ui.components.styles.styledDropDownList
import ui.theme.LIGHT_PURPLE

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
    onEvent: (RegisterInfoEvent) -> Unit,
    accountType: AccountType,
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        // Main content of the register screen
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.padding(top = 24.dp))

            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Create an Account",
                color = MaterialTheme.colorScheme.onBackground,
                style = ui.theme.Typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            AccountDetailField(
                label = "Name",
                prompt = "Enter name",
                onTextChanged = { onEvent(RegisterInfoEvent.NameChanged(it)) },
                keyboardType = KeyboardType.Text,
                value = uiState.name,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            if (accountType == AccountType.STUDENT) {
                AccountDetailField(
                    label = "Last Name",
                    prompt = "Enter name",
                    onTextChanged = { onEvent(RegisterInfoEvent.SurnameChanged(it)) },
                    keyboardType = KeyboardType.Text,
                    value = uiState.name,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.padding(top = 24.dp))
            }

            androidx.compose.material.Text(
                text = "School",
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            styledDropDownList(
                items = ModelManager.getAllSchoolList().toList(),
                modifier = Modifier,
                width = 256,
                multiSelect = false,
                noSelectionText = "Select School",
                value = uiState.schoolName,
                onSelect = { onEvent(RegisterInfoEvent.SchoolNameChanged(it)) },
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            if (accountType == AccountType.STUDENT) {
            } else {
                androidx.compose.material.Text(
                    text = "Tags",
                    color = MaterialTheme.colorScheme.onBackground,
                )

                Spacer(modifier = Modifier.padding(top = 16.dp))

                styledDropDownList(
                    items = ModelManager.getAllInterestList().toList(),
                    modifier = Modifier,
                    width = 256,
                    multiSelect = true,
                    noSelectionText = "Select Tags",
                    value = uiState.tags.joinToString(", "),
                    onSelectionChange = { uiState.tags = it.toMutableStateList()}
                )
            }

            Spacer(modifier = Modifier.weight(0.15f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                styledButton(
                    text = "Continue",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onEvent(RegisterInfoEvent.OnContinue) },
                    buttonColor = LIGHT_PURPLE,
                    textColor = Color.White,
                )

                Spacer(modifier = Modifier.weight(0.05f))

                styledButton(
                    text = "Back",
                    width = 80,
                    xAlignment = Alignment.CenterHorizontally,
                    onClick = { onEvent(RegisterInfoEvent.OnBack) },
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