package ui.views.register.info

sealed class RegisterInfoEvent {
    data object OnContinue : RegisterInfoEvent()
    data object OnBack : RegisterInfoEvent()
    data class NameChanged(val name: String) : RegisterInfoEvent()
    data class SurnameChanged(val surname: String) : RegisterInfoEvent()
    data class SchoolNameChanged(val schoolName: String) : RegisterInfoEvent()
    data class MajorChanged(val major: String) : RegisterInfoEvent()
    data class TagAdded(val tag: String) : RegisterInfoEvent()
    data class TagRemoved(val tag: String) : RegisterInfoEvent()
}