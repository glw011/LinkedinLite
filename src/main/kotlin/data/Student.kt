package data

import androidx.compose.ui.graphics.ImageBitmap
import model.ModelManager
import model.School
import model.Student
import service.StudentService
import java.util.LinkedList

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
    AccountType.STUDENT,
    profilePicture,
    tags
) {
    fun getMajor(): String {
        return major
    }

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
    override fun getProfilePicture(): ImageBitmap {
        return profilePicture
    }
    override fun getTags(): List<String> {
        val tagIDs = getModel().interestList.toList()
        val tags = mutableListOf<String>()
        for (tagID in tagIDs) {
            tags.add(ModelManager.getInterest(tagID).name)
        }
        return tags
    }

    override fun setName(name: String) {
        setFirstName(name.split(" ")[0])
        setLastName(name.split(" ")[1])
    }
    fun setFirstName(name: String) {
        getModel().fname = name
    }
    fun setLastName(surname: String) {
        getModel().lname = surname
    }

    override fun setEmail(email: String) {
        getModel().email = email
    }

    override fun setSchool(school: String) {
        getModel().school = ModelManager.getSchoolByName(school)
    }
    fun setMajor(major: String) {
        getModel().major = ModelManager.getMajorIdByName(major)
    }
    override fun setProfilePicture(profilePicture: ImageBitmap) {
        this.profilePicture = profilePicture
    }
    override fun setTags(tags: List<String>) {
        val tagIDs = mutableListOf<Int>()
        for (tag in tags) {
            tagIDs.add(ModelManager.getInterestByName(tag).id)
        }
        getModel().interestList = (tagIDs as LinkedList<Int>?)
    }
    override fun addTag(tag: String) {
        val tagIDs = getModel().interestList
        tagIDs.add(ModelManager.getInterestByName(tag).id)
        getModel().interestList = tagIDs
    }
    override fun removeTag(tag: String) {
        val tagIDs = getModel().interestList
        tagIDs.remove(ModelManager.getInterestByName(tag).id)
        getModel().interestList = tagIDs
    }

    companion object {
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
            StudentService().addStudent(
                name,
                surname,
                email,
                password,
                school,
                major
            )
            return data.Student(
                ModelManager.getUserId(email),
                name,
                surname,
                email,
                ModelManager.getSchoolByName(school),
                major,
                profilePicture,
                tags
            )
        }
    }
}