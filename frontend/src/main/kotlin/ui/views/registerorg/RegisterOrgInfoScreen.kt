package ui.views.registerorg

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
import data.DataSource.tags
import ui.components.AccountDetailField
import ui.components.styledButton
import ui.components.styledDropDownList
import ui.theme.LIGHT_PURPLE

/**
 * The registration screen allowing the user to input information about his or her organization
 *
 * @param onContinue Callback function for when the user clicks the continue button.
 * @param onBack Callback function for when the user clicks the back button.
 * @param onOrgNameChanged Callback function for when the user changes the organization name.
 * @param onSchoolNameChanged Callback function for when the user changes the school name.
 * @param onOrgTagsChanged Callback function for when the user changes the organization tags.
 */
@Composable
fun registerOrgInfoScreen(
    uiState: RegisterOrgInfoUiState,
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

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Create an Account",
                color = MaterialTheme.colorScheme.onBackground,
                style = ui.theme.Typography.bodyLarge
            )

            Spacer(modifier = Modifier.padding(top = 32.dp))

            AccountDetailField(
                label = "Organization Name",
                prompt = "Enter name",
                onTextChanged = { uiState.orgName = it },
                keyboardType = KeyboardType.Text,
                value = uiState.orgName,
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

            Text(
                text = "Organization Tags",
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            styledDropDownList(
                items = tags,
                modifier = Modifier.fillMaxWidth(),
                width = 256,
                multiSelect = true,
                noSelectionText = "Select Organization Tags",
                value = uiState.orgTags.joinToString(", "),
                onSelect = {
                    val selectedTag = it
                    if (selectedTag in uiState.orgTags) {
                        uiState.orgTags.remove(selectedTag)
                    } else {
                        uiState.orgTags.add(selectedTag)
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