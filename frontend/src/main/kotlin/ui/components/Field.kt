package ui.components

import androidx.compose.runtime.Composable

data class Field(
    val title: String,
    val prompt: String,
    val onEdit: (String) -> Unit,
)
