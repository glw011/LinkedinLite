package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileCard(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    ElevatedCard(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.3f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            )
            Text(
                text = subtitle,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
            )
            content()
        }
    }
}