package ui.components

data class Field(
    val title: String,
    val prompt: String,
    val onEdit: (String) -> Unit,
)
