package ui.views.home

import androidx.compose.runtime.mutableStateListOf
import data.User

data class NotificationsUiState(
    private val _notification: User,
) {
    var notifications = mutableStateListOf(_notification)
}