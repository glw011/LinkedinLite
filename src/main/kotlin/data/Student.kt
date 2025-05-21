package data

import ProfileRecommender
import androidx.compose.ui.graphics.ImageBitmap
import dao.StudentDAO
import dao.UserDAO
import model.ModelManager
import model.School
import model.Student
import model.UserType
import service.OrgService
import service.StudentService

class Student private constructor(
    private val id: Int,
    private var name: String,
    private var surname: String,
    private var email: String,
    private var school: School,
    private var major: String,
    private var profilePicture: ImageBitmap,
    private var tags: List<String>,
) : User(
    id,
    "$name $surname",
    email,
    school,
    UserType.STUDENT,
    profilePicture,
    tags
) {
    override val title = "Student"

    override fun getModel(): Student {
        return StudentService().getStudentById(id)
    }
    override fun getName(): String {
        return "${getModel().fname} ${getModel().lname}"
    }
    fun getFirstName(): String {
        return getModel().fname
    }
    fun getLastName(): String {
        return getModel().lname
    }
    override fun getEmail(): String {
        return getModel().email
    }
    override fun getSchool(): String {
        return getModel().school.schoolName
    }
    fun getMajor(): String {
        return major
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
    override fun getDescription(): String {
        if (getModel().bio == null) {
            return ""
        }
        return getModel().bio
    }
    fun getOrganizations(): List<Organization> {
        return getModel().orgList.toList().map { Organization.fromModel(OrgService().getOrgById(it)) }
    }

    override fun getRecommendedStudents(): List<data.Student> {
        return ProfileRecommender.recommendStudents(this, 4)
            .map { fromModel(it) }
    }
    override fun getRelatedOrganizations(): List<Organization> {
        return ProfileRecommender.recommendOrganizations(this, 4)
            .map { Organization.fromModel(it) }
    }
    fun getPendingInvites(): List<Organization> {
        return StudentDAO.getAllPendingInvites(getId()).map { Organization.fromModel(it) }
    }

    override fun setName(name: String) {
        setFirstName(name.split(" ")[0])
        setLastName(name.split(" ")[1])
    }
    fun setFirstName(name: String) {
        getModel().fname = name
        StudentService().updateStudent(getModel())
    }
    fun setLastName(surname: String) {
        StudentDAO.setLName(getId(), surname)
    }

    override fun setEmail(email: String) {
        getModel().email = email
    }

    override fun setSchool(school: String) {
        getModel().school = ModelManager.getSchoolByName(school)
        StudentDAO.setSchool(getId(), ModelManager.getSchoolByName(school).id)
        StudentService().updateStudent(getModel())
    }
    fun setMajor(major: String) {
        getModel().major = ModelManager.getMajorIdByName(major)
        StudentService().updateStudent(getModel())
    }
    override fun setProfilePicture(profilePicture: ImageBitmap) {
        this.profilePicture = profilePicture
    }
    override fun setDescription(description: String) {
        StudentDAO.setBio(getId(), description)
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
                UserDAO.addUserInterest(id, ModelManager.getInterestByName(tag).id)
            }
        }
    }
    override fun addTag(tag: String) {
        UserDAO.addUserInterest(id, ModelManager.getInterestByName(tag).id)
    }
    override fun removeTag(tag: String) {
        UserDAO.delUserInterest(id, ModelManager.getInterestByName(tag).id)
    }
    fun setOrganizations(organizations: List<Organization>) {
        for (org in organizations) {
            if (!getModel().orgList.contains(org.getId())) {
                OrgService().getOrgById(org.getId()).addMember(getId())
            }
        }
    }
    fun addOrganization(organization: Organization) {
        if (!getModel().orgList.contains(organization.getId())) {
            OrgService().getOrgById(organization.getId()).addMember(getId())
        }
    }
    fun removeOrganization(organization: Organization) {
        if (getModel().orgList.contains(organization.getId())) {
            OrgService().getOrgById(organization.getId()).removeMember(getId())
        }
    }
    fun requestMembership(organization: Organization) {
        if (!getModel().orgList.contains(organization.getId())) {
            StudentDAO.requestMembership(getId(), organization.getId())
        }
    }
    fun acceptMembership(organization: Organization) {
        StudentDAO.approveOrgInvite(getId(), organization.getId())
    }
    fun rejectMembership(organization: Organization) {
        StudentDAO.denyOrgInvite(getId(), organization.getId())
    }

    companion object {
        fun fromModel(student: Student): data.Student {
            val tags = mutableListOf<String>()
            for (tagID in student.interests) {
                tags.add(ModelManager.getInterest(tagID).name)
            }
            return Student(
                student.id,
                student.fname,
                student.lname,
                student.email,
                student.school,
                ModelManager.getMajor(student.major).name,
                ImageBitmap(0, 0),
                tags
            )
        }

        fun register(
            name: String,
            surname: String,
            email: String,
            password: String,
            school: String,
            major: String,
            profilePicture: ImageBitmap,
            tags: List<String>,
        ): data.Student {
            val id = StudentService().addStudent(
                name,
                surname,
                email,
                password,
                school,
                major
            )
            for (tag in tags) {
                UserDAO.addUserInterest(id, ModelManager.getInterestByName(tag).id)
            }
            return Student(
                id,
                name,
                surname,
                email,
                ModelManager.getSchoolByName(school),
                major,
                profilePicture,
                tags
            )
        }

        fun login(email: String): data.Student {
            val student = StudentService().getStudentById(ModelManager.getUserId(email))
            return if (student != null) {
                fromModel(student)
            } else {
                throw Exception("Invalid user")
            }
        }
    }
}