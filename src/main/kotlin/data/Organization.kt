package data

import ProfileRecommender
import androidx.compose.ui.graphics.ImageBitmap
import dao.OrgDAO
import dao.PictureDAO
import dao.UserDAO
import imageBitmapToBufferedImage
import model.ModelManager
import model.Org
import model.School
import model.UserType
import service.OrgService
import service.StudentService

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
    override fun getDescription(): String {
        if (getModel().bio == null) {
            return ""
        }
        return getModel().bio
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
    fun getMembers(): List<Student> {
        return getModel().membersList.map { Student.fromModel(StudentService().getStudentById(it)) }
    }
    fun getMemberRole(member: Student): String {
        val role = OrgService().getMemberRole(member.getId(), getId())
        if (role.isEmpty()) {
            return "Member"
        } else {
            return role
        }
    }
    fun getPendingMembershipRequests(): List<Student> {
        val pendingRequests = OrgService().getPendingRequests(getId())
        if (pendingRequests == null) {
            return emptyList()
        } else {
            return pendingRequests.map { Student.fromModel(it) }
        }
    }

    override fun setName(name: String) {
        getModel().name = name
    }

    override fun setEmail(email: String) {
        getModel().email = email
    }

    override fun setSchool(school: String) {
        OrgDAO.setSchool(getId(), ModelManager.getSchoolIdByName(school))
    }
    override fun setDescription(description: String) {
        OrgDAO.setBio(getId(), description)
    }
    override fun setTags(tags: List<String>) {
        val currentTags = getModel().interests.toList()
        for (tagID in currentTags) {
            if (!tags.contains(ModelManager.getInterest(tagID).name)) {
                UserDAO.delUserInterest(id, tagID)
            }
        }
        for (tag in tags) {
            if (!getModel().interests.contains(ModelManager.getInterestByName(tag).id)) {
                UserDAO.addUserInterest(getId(), ModelManager.getInterestByName(tag).id)
            }
        }
    }
    override fun addTag(tag: String) {
        UserDAO.addUserInterest(getId(), ModelManager.getInterestByName(tag).id)
    }
    override fun removeTag(tag: String) {
        UserDAO.delUserInterest(getId(), ModelManager.getInterestByName(tag).id)
    }
    fun setMembers(members: List<Student>) {
        for (member in members) {
            if (!getModel().membersList.contains(member.getId())) {
                OrgService().addMember(getId(), member.getId())
            }
        }
    }
    fun addMember(member: Student) {
        OrgService().addMember(getId(), member.getId())
    }
    fun removeMember(member: Student) {
        OrgService().delMember(getId(), member.getId())
    }
    fun inviteMember(member: Student) {
        OrgService().inviteMember(getId(), member.getId())
    }
    fun acceptMember(member: Student) {
        try {
            OrgService().approveMemberRequest(getId(), member.getId())
        } catch (e: Exception) {
            // Let the exception pass
        }
    }
    fun rejectMember(member: Student) {
        OrgService().denyMemberRequest(getId(), member.getId())
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
            UserDAO.setProfileImg(id, PictureDAO.addNewImg(imageBitmapToBufferedImage(profilePicture), id))
            for (tag in tags) {
                OrgDAO.addUserInterest(id, ModelManager.getInterestByName(tag).id)
            }
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