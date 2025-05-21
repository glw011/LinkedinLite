package ui.views.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ImageBitmap
import data.Organization
import data.Student
import data.User
import ui.components.profilecard.Associate

/**
 * Data class holding the UI state for a profile screen
 * @property headerInfo The header information of the profile.
 * @property interestedPeople A list of people the profile may be interested in.
 * @property relatedOrganizations A list of organizations related to the profile.
 * @property members A list of members associated with the profile. (For organization accounts)
 * @property tags A list of tags associated with the profile.
 */
data class ProfileUiState(
    var user: User,
    var recommendedPeople: List<Student> = listOf(),
    var relatedOrganizations: List<Organization> = listOf(),
    var associates: List<Associate> = listOf(),
) {
    var headerInfo: ProfileHeaderInfo = ProfileHeaderInfo(
        _name = user.getName(),
        _description = user.getDescription(),
        _title = user.title,
        _location = user.getLocation(),
        _school = user.getSchool(),
    )
    var tags = user.getTags()
}

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
    private var _name: String = "",
    private var _description: String = "",
    private var _title: String = "",
    private var _location: String = "",
    private var _school: String = "",
){
    var name by mutableStateOf(_name)
    var description by mutableStateOf(_description)
    var title by mutableStateOf(_title)
    var location by mutableStateOf(_location)
    var school by mutableStateOf(_school)
    var banner by mutableStateOf(ImageBitmap(0, 0))
    var profilePicture by mutableStateOf(ImageBitmap(0, 0))
}