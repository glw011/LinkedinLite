package ui.views.register

import androidx.compose.runtime.mutableStateListOf

/**
 * UI state for the RegisterOrgInfo screen.
 *
 * @param orgName The name of the organization.
 * @param schoolName The name of the school.
 * @param orgTags A list of tags associated with the organization.
 */
data class RegisterOrgInfoUiState (
    var orgName: String = "",
    var schoolName: String = "",
    var orgTags: MutableList<String> = mutableStateListOf(),
)