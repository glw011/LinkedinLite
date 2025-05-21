package ui.views.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.styles.styledButton

@Composable
fun SettingsTab(onLogout: () -> Unit) {
    Box(
       modifier = Modifier.fillMaxSize()
    ) {
        styledButton(
            text = "Logout",
            onClick = {
                onLogout()
            },
            width = 200,
            buttonColor = Color.Red,
            textColor = Color.White,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
        )
    }
}