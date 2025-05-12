package ui.components.profilecard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.AccountType
import data.User
import data.current_user
import ui.components.image.PfpImage

fun getRecommendedUsers(users: List<User>, accountType: AccountType?): List<User> {
    val possibleUsers = users.toMutableList()

    // Remove the current user from the list
    possibleUsers.removeIf { it.getId() == current_user.getId() }

    // Filter users based on the account type
    if (accountType == AccountType.ORGANIZATION) {
        // Remove users that are not organizations
        possibleUsers.removeIf { it.accountType != AccountType.ORGANIZATION }
    } else if (accountType == AccountType.INDIVIDUAL) {
        // Remove users that are not individuals
        possibleUsers.removeIf { it.accountType != AccountType.INDIVIDUAL }
    }

    // Remove users that are already followed
    possibleUsers.removeIf { current_user.following.contains(it) }

    val userScores = hashMapOf<User, Int>()

    // Calculate scores for each user based on tags, followers, and associates
    for (user in possibleUsers) {
        var currentScore = 0

        for (tag in user.tags) {
            if (current_user.tags.contains(tag)) {
                currentScore++
            }
        }

        for (follower in user.followers) {
            if (current_user.followers.contains(follower)) {
                currentScore++
            }
        }

        for (associate in user.associates) {
            if (current_user.associates.contains(associate)) {
                currentScore += 2
            } else {
                for (tag in associate.tags) {
                    if (current_user.tags.contains(tag)) {
                        currentScore++
                    }
                }
            }
        }

        // If the current user and target user are both students, compare the tags in their organizations
        val bothUsersAreStudents = current_user.accountType == AccountType.INDIVIDUAL && user.accountType == AccountType.INDIVIDUAL
        if (bothUsersAreStudents) {
            // Get the tags of the organizations associated with both users
            val currentUserOrgTags = current_user.associates.flatMap { it.tags }.toSet()
            val targetUserOrgTags = user.associates.flatMap { it.tags }.toSet()

            // Calculate the score based on the intersection of tags
            currentScore += currentUserOrgTags.intersect(targetUserOrgTags).size
        }

        // Record the score for the user
        userScores[user] = currentScore
    }

    val recommendedUsers = userScores.entries
        .sortedByDescending { it.value }
        .take(5)
        .map { it.key }

    for (user in recommendedUsers) {
        println(user.name + ": " + userScores[user])
    }

    return recommendedUsers
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
            imageBitmap = null,
            modifier = Modifier.weight(0.4f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
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
        modifier = modifier
    ) {
        ProfileSlot(
            name = "John Doe",
            connection = "x Mutual Connections",
            modifier = Modifier.height(64.dp)
        )
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
        modifier = modifier
    ) {
        ProfileSlot(
            name = "Louisiana Tech University",
            connection = "x Tags of Interest",
            modifier = Modifier.height(64.dp)
        )
    }
}