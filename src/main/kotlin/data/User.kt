package data

import androidx.compose.ui.graphics.ImageBitmap
import model.School
import model.UserType

abstract class User(
    private val id: Int,
    private var name: String,
    private var email: String,
    private var school: School,
    private val accountType: UserType,
    private var profilePicture: ImageBitmap,
    private var tags: List<String>,
) {
    abstract val title: String
    protected var description: String = ""

    protected var profileBanner: ImageBitmap = ImageBitmap(0, 0)


    abstract fun getModel(): Any

    fun getId(): Int {
        return id
    }
    abstract fun getName(): String
    abstract fun getEmail(): String
    abstract fun getSchool(): String
    fun getLocation(): String {
        return "${school.city}, ${school.state}, ${school.country}"
    }
    abstract fun getProfilePicture(): ImageBitmap
    abstract fun getTags(): List<String>
    abstract fun getRecommendedStudents(): List<Student>
    abstract fun getRelatedOrganizations(): List<Organization>
    fun getAccountType(): UserType {
        return accountType
    }

    abstract fun setName(name: String)
    abstract fun setEmail(email: String)
    abstract fun setSchool(school: String)
    abstract fun setProfilePicture(profilePicture: ImageBitmap)
    abstract fun setTags(tags: List<String>)
    abstract fun addTag(tag: String)
    abstract fun removeTag(tag: String)
}