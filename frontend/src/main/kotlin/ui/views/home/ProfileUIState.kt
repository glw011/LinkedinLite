package ui.views.home

import androidx.compose.ui.graphics.ImageBitmap
import org.example.linkedinliteui.generated.resources.Res
import org.example.linkedinliteui.generated.resources.default_banner
import org.example.linkedinliteui.generated.resources.default_pfp
import util.getBitmapFromDrawableID

/**
 * Data class holding the UI state for a profile screen
 * @property headerInfo The header information of the profile.
 * @property interestedPeople A list of people the profile may be interested in.
 * @property relatedOrganizations A list of organizations related to the profile.
 * @property members A list of members associated with the profile. (For organization accounts)
 * @property roles A list of roles associated with the profile.
 * @property tags A list of tags associated with the profile.
 */
data class ProfileUiState(
    var headerInfo: ProfileHeaderInfo = ProfileHeaderInfo(),
    var interestedPeople: List<String> = listOf(),
    var relatedOrganizations: List<String> = listOf(),
    var members: List<String> = listOf(),
    var roles: List<String> = listOf(),
    var tags: List<String> = listOf(),
)

/**
 * @property name The name of the profile owner.
 * @property description A short description or bio of the profile.
 * @property title The title of the profile ("Organization" or "Student").
 * @property location The location of the profile owner (City, State).
 * @property school The name of the school the profile belongs to.
 * @property banner The ImageBitmap for the profile banner. Nullable.
 * @property profilePicture The ImageBitmap for the profile picture. Nullable.
 */
data class ProfileHeaderInfo(
    var name: String = "<Placeholder Name>",
    var description: String = "<Placeholder Description>",
    var title: String = "<Placeholder Title>",
    var location: String = "<Placeholder Location>",
    var school: String = "<Placeholder School>",
    var banner: ImageBitmap? = getBitmapFromDrawableID(Res.drawable.default_banner),
    var profilePicture: ImageBitmap? = getBitmapFromDrawableID(Res.drawable.default_pfp),
)