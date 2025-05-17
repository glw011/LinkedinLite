package data

import model.ModelManager
import model.Org
import model.Student
import service.OrgService
import service.StudentService

enum class AccountType {
    STUDENT,
    ORGANIZATION
}

sealed interface UserModel

data class StudentModel(
    val id: Int,
    val name: String,
    val email: String,
    val school: String,
) : UserModel {
    fun toStudent(): Student {
        return Student(
            id,
            email,
            name.split(" ")[0],
            name.split(" ")[1],
            ModelManager.getSchoolByName(school)
        )
    }
    companion object {
        fun fromStudent(student: Student): StudentModel {
            return StudentModel(
                student.id,
                "${student.fname} ${student.lname}",
                student.email,
                student.school.name
            )
        }
    }
}

data class OrgModel(
    val id: Int,
    val name: String,
    val email: String,
    val school: String,
) : UserModel {
    fun toOrg(): Org {
        return Org(
            id,
            email,
            name.split(" ")[0],
            ModelManager.getSchoolByName(school)
        )
    }
    companion object {
        fun fromOrg(org: Student): OrgModel {
            return OrgModel(
                org.id,
                "${org.fname} ${org.lname}",
                org.email,
                org.school.name
            )
        }
    }
}

class User private constructor(
    private val id: Int,
    private var name: String,
    private var email: String,
    private var school: String,
    private val accountType: AccountType,
) {
    val model: UserModel = when (accountType) {
        AccountType.STUDENT -> StudentModel(id, name, email, school)
        AccountType.ORGANIZATION -> OrgModel(id, name, email, school)
        else -> throw IllegalArgumentException("Invalid account type")
    }

    fun getId(): Int {
        return id
    }
    private fun getStudent() : Student{
        return when (model) {
            is StudentModel -> StudentService().getStudentById(id)
            is OrgModel -> throw IllegalArgumentException("Org model cannot be converted to Student")
        }
    }
    private fun getOrg() : Org{
        return when (model) {
            is StudentModel -> throw IllegalArgumentException("Student model cannot be converted to Org")
            is OrgModel -> OrgService().getOrgById(id)
        }
    }
    fun getName(): String {
        return when (model) {
            is StudentModel -> getStudent().fname + " " + getStudent().lname
            is OrgModel -> getOrg().name
        }
    }
    fun getEmail(): String {
        return when (model) {
            is StudentModel -> getStudent().email
            is OrgModel -> getOrg().email
        }
    }
    fun getSchool(): String {
        return when (model) {
            is StudentModel -> getStudent().school.schoolName
            is OrgModel -> getOrg().school.schoolName
        }
    }
    fun getAccountType(): AccountType {
        return accountType
    }
    fun setName(name: String) {
        when (model) {
            is StudentModel -> {
                getStudent().fname = name.split(" ")[0]
                getStudent().lname = name.split(" ")[1]
            }
            is OrgModel -> getOrg().name = name
        }
    }
    fun setEmail(email: String) {
        when (model) {
            is StudentModel -> getStudent().email = email
            is OrgModel -> getOrg().email = email
        }
    }
    fun setSchool(school: String) {
        when (model) {
            is StudentModel -> getStudent().school = ModelManager.getSchoolByName(school)
            is OrgModel -> getOrg().school = ModelManager.getSchoolByName(school)
        }
    }

    companion object {
        fun register(
            name: String,
            email: String,
            school: String,
            accountType: AccountType
        ): data.User {
            if (accountType == AccountType.STUDENT) {
                StudentService().addStudent(
                    name.split(" ")[0],
                    email,
                    "example",
                    school,
                    "Computer Science"
                )
            } else {
                OrgService().createOrg(
                    name,
                    email,
                    "example",
                    ModelManager.getSchoolIdByName(school)
                )
            }
            return data.User(
                ModelManager.getUserId(email),
                name,
                email,
                school,
                accountType
            )
        }
    }
}