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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import model.ModelManager
import ui.components.styles.styledButton
import ui.components.styles.styledDropDownList
import ui.theme.LIGHT_PURPLE

@Composable
fun registerInfoScreenCont(
    uiState: RegisterInfoUiState,
    onBack: () -> Unit,
    onContinue: () -> Unit,
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

            androidx.compose.material.Text(
                text = "Interests",
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            styledDropDownList(
                items = ModelManager
                    .getAllInterestList()
                    .toList()
                    .sortedByDescending { it }
                    .asReversed(),
                modifier = Modifier,
                width = 256,
                multiSelect = true,
                noSelectionText = "Select Tags",
                value = uiState.tags.joinToString(", "),
                onSelectionChange = { uiState.tags = it.toMutableStateList() }
            )

            Spacer(modifier = Modifier.padding(top = 24.dp))

            androidx.compose.material.Text(
                text = "Major",
                color = MaterialTheme.colorScheme.onBackground,
            )

            Spacer(modifier = Modifier.padding(top = 16.dp))

            styledDropDownList(
                items = ModelManager
                    .getAllMajorList()
                    .toList()
                    .sortedByDescending { it }
                    .asReversed(),
                modifier = Modifier,
                width = 256,
                multiSelect = false,
                noSelectionText = "Select Major",
                value = uiState.major,
                onSelect = { uiState.major = it },
            )

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