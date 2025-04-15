package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * Composable function for an account detail field.
 *
 * This function renders a Text composable above a TextField composable. It is used for
 * displaying and editing account details such as username, email, and password.
 *
 * @param label The label for the field, displayed above the TextField.
 * @param prompt The placeholder text for the TextField.
 * @param keyboardType The type of keyboard to use for the TextField.
 * @param password Boolean indicating whether the field is for a password (true) or not (false).
 * @param modifier Modifier to be applied to the TextField.
 */
@Composable
fun AccountDetailField(
    label: String,
    prompt: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable() { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.padding(top = 16.dp))

        styledTextField(
            onTextChanged = { value = it },
            width = 256,
            xAlignment = Alignment.CenterHorizontally,
            unfocusedText = prompt,
            isPassword = password
        )
    }
}