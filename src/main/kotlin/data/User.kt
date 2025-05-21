package data

import androidx.compose.ui.graphics.ImageBitmap
import dao.PictureDAO
import dao.UserDAO
import imageBitmapToBufferedImage
import model.School
import model.UserType
import util.getBitmapFromFilepath
import util.writeToFile

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

    private var profileBanner: ImageBitmap = ImageBitmap(0, 0)


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
    fun getProfileBanner(): ImageBitmap {
        return getBitmapFromFilepath(PictureDAO.getImgPath(PictureDAO.getBannerImgId(getId())))
    }
    fun getProfilePicture(): ImageBitmap {
        return getBitmapFromFilepath(PictureDAO.getImgPath(PictureDAO.getProfileImgId(getId())))
    }
    abstract fun getDescription(): String
    abstract fun getTags(): List<String>
    abstract fun getRecommendedStudents(): List<Student>
    abstract fun getRelatedOrganizations(): List<Organization>
    fun getAccountType(): UserType {
        return accountType
    }

    abstract fun setName(name: String)
    abstract fun setEmail(email: String)
    abstract fun setSchool(school: String)
    fun setProfileBanner(profileBanner: ImageBitmap) {
        UserDAO.setBannerImg(getId(), PictureDAO.addNewImg(imageBitmapToBufferedImage(profileBanner), getId()))
    }
    fun setProfilePicture(profilePicture: ImageBitmap) {
        UserDAO.setProfileImg(getId(), PictureDAO.addNewImg(imageBitmapToBufferedImage(profilePicture), getId()))
    }
    abstract fun setDescription(description: String)
    abstract fun setTags(tags: List<String>)
    abstract fun addTag(tag: String)
    abstract fun removeTag(tag: String)

    fun logout() {
        writeToFile("")
    }
}