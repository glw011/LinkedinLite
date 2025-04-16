package data

import androidx.compose.ui.graphics.ImageBitmap

data class ProfileUiState(
    val banner: ImageBitmap? = null,
    val profilePicture: ImageBitmap? = null,
    val name: String = "",
    val title: String = "",
    val location: String = "",
    val school: String = "",
    val description: String = "",
    val interestedPeople: List<String> = listOf(),
    val relatedOrganizations: List<String> = listOf(),
    val members: List<String> = listOf(),
    val roles: List<String> = listOf(),
    val tags: List<String> = listOf(),
)