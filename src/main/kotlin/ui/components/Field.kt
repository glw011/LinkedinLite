package ui.components

/**
 * A data class representing a field in a form or UI component.
 *
 * @property title The title of the field.
 * @property prompt The prompt or hint text for the field.
 * @property onEdit The callback function to be called when the field is edited.
 * @property value The current value of the field.
 */
data class Field(
    val title: String,
    val prompt: String,
    val onEdit: (String) -> Unit,
    var value: String = "",
)
