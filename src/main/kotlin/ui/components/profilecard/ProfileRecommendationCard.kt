package ui.components.profilecard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import data.User
import model.UserType
import ui.components.image.PfpImage

// TODO: Replace with actual user list
fun getRecommendedUsers(users: List<User>, userType: UserType): List<User> {
//    val possibleUsers = users.toMutableList()
//
//    // Remove the current user from the list
//    possibleUsers.removeIf { it.getId() == current_user.getId() }
//
//    // Filter users based on the account type
//    if (userType == UserType.ORG) {
//        // Remove users that are not organizations
//        possibleUsers.removeIf { it.accountType == UserType.STUDENT }
//    } else if (userType == UserType.STUDENT) {
//        // Remove users that are not individuals
//        possibleUsers.removeIf { it.accountType == UserType.ORG }
//    }
//
//    // Remove users that are already followed
//    possibleUsers.removeIf { current_user.following.contains(it) }
//
//    val userScores = hashMapOf<User, Int>()
//
//    // Calculate scores for each user based on tags, followers, and associates
//    for (user in possibleUsers) {
//        var currentScore = 0
//
//        for (tag in user.tags) {
//            if (current_user.tags.contains(tag)) {
//                currentScore++
//            }
//        }
//
//        for (follower in user.followers) {
//            if (current_user.followers.contains(follower)) {
//                currentScore++
//            }
//        }
//
//        for (associate in user.associates) {
//            if (current_user.associates.contains(associate)) {
//                currentScore += 2
//            } else {
//                for (tag in associate.tags) {
//                    if (current_user.tags.contains(tag)) {
//                        currentScore++
//                    }
//                }
//            }
//        }
//
//        // If the current user and target user are both students, compare the tags in their organizations
//        val bothUsersAreStudents = current_user.accountType == AccountType.INDIVIDUAL && user.accountType == AccountType.INDIVIDUAL
//        if (bothUsersAreStudents) {
//            // Get the tags of the organizations associated with both users
//            val currentUserOrgTags = current_user.associates.flatMap { it.tags }.toSet()
//            val targetUserOrgTags = user.associates.flatMap { it.tags }.toSet()
//
//            // Calculate the score based on the intersection of tags
//            currentScore += currentUserOrgTags.intersect(targetUserOrgTags).size
//        }
//
//        // Record the score for the user
//        userScores[user] = currentScore
//    }
//
//    // TODO: Randomly sample users with the same score to recommend
//    // TODO: Only include two users from the same organization
//    // TODO: Get connections type (E.g. x followers, y associates, etc.) and return it
//    val recommendedUsers = userScores.entries
//        .sortedByDescending { it.value }
//        .take(4)
//        .map { it.key }
//
//    for (user in recommendedUsers) {
//        println(user.name + ": " + userScores[user])
//    }
//
//    return recommendedUsers
    return users
}

/**
 * Creates a slot for a profile to be used in a recommendation card.
 *
 * @param name The name of the profile.
 * @param connection The connection status of the profile.
 * @param modifier The modifier for the profile slot.
 */
@Composable
fun ProfileSlot(
    name: String,
    connection: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        PfpImage(
            imageBitmap = ImageBitmap(0, 0),
            modifier = Modifier.weight(0.4f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = connection,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

/**
 * Creates a recommendation card for profiles. Used on a profile page to display
 * recommendations for people or organizations to connect with.
 *
 * @param isPersonalProfile Boolean indicating if the profile is personal or organizational.
 * @param modifier Modifier for the card.
 */
@Composable
fun ProfileRecommendationCard(
    isPersonalProfile: Boolean,
    modifier: Modifier = Modifier,
) {
    if (isPersonalProfile) {
        ProfileCard(
            title = "People you may know",
            subtitle = "",
            isEditable = false,
            modifier = modifier
        ) {
            PersonalProfileRecommendationCard()
        }
    } else {
        ProfileCard(
            title = "Organizations you may be interested in",
            subtitle = "",
            isEditable = false,
            modifier = modifier,
        ) {
            OrganizationProfileRecommendationCard()
        }
    }
}

/**
 * Creates a recommendation card for personal profiles.
 *
 * @param modifier Modifier for the card.
 */
@Composable
fun PersonalProfileRecommendationCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
//        val profiles = getRecommendedUsers(
//            users = testUsers, // TODO: Replace with actual user list
//            accountType = AccountType.INDIVIDUAL
//        )
        for (i in 1..3) {
            ProfileSlot(
                name = "Example Name $i",
                connection = "x Tags of Interest",
                modifier = Modifier.height(64.dp)
            )
        }
    }
}

/**
 * Creates a recommendation card for organizational profiles.
 *
 * @param modifier Modifier for the card.
 */
@Composable
fun OrganizationProfileRecommendationCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
//        val profiles = getRecommendedUsers(
//            users = testUsers, // TODO: Replace with actual user list
//            accountType = AccountType.ORGANIZATION
//        )
        for (i in 1..3) {
            ProfileSlot(
                name = "Example Org Name $i",
                connection = "x Tags of Interest",
                modifier = Modifier.height(64.dp)
            )
        }
    }
}