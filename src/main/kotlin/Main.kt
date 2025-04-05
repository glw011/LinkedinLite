import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        //Button(onClick = { text = "Hello, Desktop!" }) { Text(text) }
        ContentBanner()
    }
}

@Composable
fun ContentBanner() {
    Scaffold (
        topBar = {
            TopAppBar (
                title = { Text("Create an Account") }
            )
            BottomAppBar (
                content = {
                    Button(onClick = { /* TODO */}) {
                        Text("Create Account")
                    }
                }
            )
        }
    ) {
        Column (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Email")
            EmailTextField()
            Text("Password:")
            PasswordTextField()
        }
    }
}

//@Composable
//fun AccountDetailField(title: String, prompt: String) {
//    var value by remmeberSaveable() { mutableStateOf("") }
//
//    Column() {
//        Text()
//    }
//}

@Composable
fun EmailTextField() {
    var email by rememberSaveable() {mutableStateOf("")}

    TextField(
        value = email,
        onValueChange = { email = it },
        label = { Text("Enter email") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        )
    )
}

@Composable
fun PasswordTextField() {
    var password by rememberSaveable() { mutableStateOf("") }

    TextField(
        value = password,
        onValueChange = { password = it },
        label = { Text("Enter password") },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
    )
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) { App() }
}
