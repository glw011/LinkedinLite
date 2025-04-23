package data

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Data class holding the UI state for a profile screen
 *
 * @property banner The ImageBitmap for the profile banner. Nullable.
 * @property profilePicture The ImageBitmap for the profile picture. Nullable.
 * @property name The name of the profile owner.
 * @property title The title of the profile ("Organization" or "Student").
 * @property location The location of the profile owner (City, State).
 * @property school The name of the school the profile belongs to.
 * @property description A short description or bio of the profile.
 * @property interestedPeople A list of people the profile may be interested in.
 * @property relatedOrganizations A list of organizations related to the profile.
 * @property members A list of members associated with the profile. (For organization accounts)
 * @property roles A list of roles associated with the profile.
 * @property tags A list of tags associated with the profile.
 */
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