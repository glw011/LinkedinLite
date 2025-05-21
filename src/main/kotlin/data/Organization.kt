package data

import ProfileRecommender
import androidx.compose.ui.graphics.ImageBitmap
import model.ModelManager
import model.Org
import model.School
import model.UserType
import service.OrgService
import java.util.*

class Organization private constructor(
    private val id: Int,
    private var name: String,
    private var email: String,
    private var school: School,
    private var profilePicture: ImageBitmap,
    private var tags: List<String>,
) : User(
    id,
    name,
    email,
    school,
    UserType.ORG,
    profilePicture,
    tags,
) {
    override val title = "Organization"

    override fun getModel(): Org {
        return OrgService().getOrgById(id)
    }
    override fun getName(): String {
        return getModel().name
    }
    override fun getEmail(): String {
        return getModel().email
    }
    override fun getSchool(): String {
        return getModel().school.schoolName
    }
    override fun getProfilePicture(): ImageBitmap {
        return profilePicture
    }
    override fun getTags(): List<String> {
        val tagIDs = getModel().interests.toList()
        val tags = mutableListOf<String>()
        for (tagID in tagIDs) {
            tags.add(ModelManager.getInterest(tagID).name)
        }
        return tags
    }
    override fun getRecommendedStudents(): List<Student> {
        return ProfileRecommender.recommendStudents(this, 4)
            .map { Student.fromModel(it) }
    }
    override fun getRelatedOrganizations(): List<Organization> {
        return ProfileRecommender.recommendOrganizations(this, 4)
            .map { fromModel(it) }
    }

    override fun setName(name: String) {
        getModel().name = name
    }

    override fun setEmail(email: String) {
        getModel().email = email
    }

    override fun setSchool(school: String) {
        getModel().school = ModelManager.getSchoolByName(school)
    }
    override fun setProfilePicture(profilePicture: ImageBitmap) {
        this.profilePicture = profilePicture
    }
    override fun setTags(tags: List<String>) {
        val tagIDs = mutableListOf<Int>()
        for (tag in tags) {
            tagIDs.add(ModelManager.getInterestByName(tag).id)
        }
        getModel().interests = (tagIDs as LinkedList<Int>?)
    }
    override fun addTag(tag: String) {
        val tagIDs = getModel().interests
        tagIDs.add(ModelManager.getInterestByName(tag).id)
        getModel().interests = tagIDs
    }
    override fun removeTag(tag: String) {
        val tagIDs = getModel().interests
        tagIDs.remove(ModelManager.getInterestByName(tag).id)
        getModel().interests = tagIDs
    }

    companion object {
        fun fromModel(organization: Org): Organization {
            val tags = mutableListOf<String>()
            for (tagID in organization.interests) {
                tags.add(ModelManager.getInterest(tagID).name)
            }
            return Organization(
                organization.id,
                organization.name,
                organization.email,
                organization.school,
                ImageBitmap(0, 0),
                tags
            )
        }

        fun register(
            name: String,
            email: String,
            password: String,
            school: String,
            profilePicture: ImageBitmap,
            tags: List<String>,
        ): Organization {
            val id = OrgService().createOrg(
                name,
                email,
                password,
                ModelManager.getSchoolIdByName(school),
            )
            return Organization(
                id,
                name,
                email,
                ModelManager.getSchoolByName(school),
                profilePicture,
                tags
            )
        }

        fun login(email: String): Organization {
            val organization = OrgService().getOrgById(ModelManager.getUserId(email))
            return if (organization != null) {
                fromModel(organization)
            } else {
                throw Exception("Invalid user")
            }
        }
    }
}