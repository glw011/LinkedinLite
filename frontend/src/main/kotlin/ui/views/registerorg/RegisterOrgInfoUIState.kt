package ui.views.registerorg

import androidx.compose.runtime.mutableStateListOf

data class RegisterOrgInfoUiState (
    var orgName: String = "",
    var schoolName: String = "",
    var orgTags: MutableList<String> = mutableStateListOf(),
)