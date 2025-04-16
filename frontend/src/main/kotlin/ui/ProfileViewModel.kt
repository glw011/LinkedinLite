package ui

import androidx.compose.ui.graphics.ImageBitmap
import data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setProfilePicture(imageBitmap: ImageBitmap?) {
        _uiState.value = _uiState.value.copy(profilePicture = imageBitmap)
    }
    fun setBanner(imageBitmap: ImageBitmap?) {
        _uiState.value = _uiState.value.copy(banner = imageBitmap)
    }
    fun setName(name: String) {
        _uiState.update() { currentState ->
            currentState.copy(name = name)
        }
    }
    fun setTitle(title: String) {
        _uiState.value = _uiState.value.copy(title = title)
    }
    fun setLocation(location: String) {
        _uiState.value = _uiState.value.copy(location = location)
    }
    fun setSchool(school: String) {
        _uiState.update() { currentState ->
            currentState.copy(school = school)
        }
    }
    fun setDescription(description: String) {
        _uiState.value = _uiState.value.copy(description = description)
    }
    fun setInterestedPeople(people: List<String>) {
        _uiState.value = _uiState.value.copy(interestedPeople = people)
    }
    fun setRelatedOrganizations(organizations: List<String>) {
        _uiState.value = _uiState.value.copy(relatedOrganizations = organizations)
    }
    fun setMembers(members: List<String>) {
        _uiState.value = _uiState.value.copy(members = members)
    }
    fun setRoles(roles: List<String>) {
        _uiState.value = _uiState.value.copy(roles = roles)
    }
    fun setTags(tags: List<String>) {
        _uiState.value = _uiState.value.copy(tags = tags)
    }
}