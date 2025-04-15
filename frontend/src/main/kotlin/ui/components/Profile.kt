package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.MainTheme

@Composable
fun Profile(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    // Profile screen UI
    MainTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
                //.verticalScroll(rememberScrollState()),
        ) {
            content()
        }
    }
}