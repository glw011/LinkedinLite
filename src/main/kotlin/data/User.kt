package data

import androidx.compose.ui.graphics.ImageBitmap
import data.AccountType.INDIVIDUAL
import data.AccountType.ORGANIZATION

/**
 * Enum class representing the type of account.
 *
 * @property ORGANIZATION Represents an organization account.
 * @property INDIVIDUAL Represents an individual account.
 */
enum class AccountType {
    ORGANIZATION,
    INDIVIDUAL,
}

/**
 * Data class representing a user account.
 *
 * @property name The name of the user.
 * @property surname The surname of the user.
 * @property schoolName The name of the school the user is associated with.
 * @property tags A list of tags associated with the user.
 * @property profilePicture The profile picture of the user. Nullable.
 * @property banner The banner image of the user. Nullable.
 * @property location The location of the user (City, State).
 * @property description A short description or bio of the user.
 * @property title The title of the user (e.g., "Student", "Organization").
 * @property accountType The type of account (individual or organization).
 */
data class User(
//    val email: String,
//    val password: String,
    var name: String = "",
    var surname: String = "",
    var schoolName: String = "",
    var tags: MutableList<String> = mutableListOf(),
    var profilePicture: ImageBitmap? = null,
    var banner: ImageBitmap? = null,
    var location: String = "",
    var description: String = "",
    var title: String = "",
    var accountType: AccountType = AccountType.INDIVIDUAL,
)
