package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ui.components.styles.styledTextField

/**
 * Creates a text field with a label and a prompt. Used for allowing the user
 * to enter their account details.
 *
 * @param label The label for the text field.
 * @param prompt The prompt in the text field.
 * @param onTextChanged The callback for when the text is changed.
 * @param keyboardType The keyboard type for the text field. (E.g. Email, Password, etc.)
 * @param password Whether the text field will contain a password or not.
 * @param modifier The modifier for the text field.
 */
@Composable
fun AccountDetailField(
    label: String,
    prompt: String,
    onTextChanged: (String) -> Unit = {},
    keyboardType: KeyboardType = KeyboardType.Text,
    password: Boolean = false,
    value: String = "",
    modifier: Modifier = Modifier
) {

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
            onTextChanged = onTextChanged,
            width = 256,
            xAlignment = Alignment.CenterHorizontally,
            unfocusedText = prompt,
            isPassword = password,
            value = value,
        )
    }
}