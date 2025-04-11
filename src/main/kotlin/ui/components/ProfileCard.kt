package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProfileCard(
    title: String,
    subtitle: String,
    content: @Composable RowScope.() -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Left,
        )
        Text(
            text = subtitle,
            modifier = Modifier.weight(0.75f),
            textAlign = TextAlign.Left,
        )
        Row(
            modifier = Modifier.weight(2f),
            content = content
        )
    }
}