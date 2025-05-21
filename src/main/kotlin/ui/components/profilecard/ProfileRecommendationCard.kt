package ui.components.profilecard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import data.User
import ui.components.image.PfpImage

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
    description: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(64.dp)
            .fillMaxWidth()
    ) {
        PfpImage(
            imageBitmap = ImageBitmap(0, 0),
            modifier = Modifier.size(64.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
            )
            var suffix = ""
            if (description.length > 40) {
                suffix = "..."
            }
            Text(
                text = description.take(40) + suffix,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
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
    user: User,
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
            PersonalProfileRecommendationCard(user)
        }
    } else {
        ProfileCard(
            title = "Organizations you may be interested in",
            subtitle = "",
            isEditable = false,
            modifier = modifier,
        ) {
            OrganizationProfileRecommendationCard(user)
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
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val profiles = user.getRecommendedStudents()
        for (profile in profiles) {
            ProfileSlot(
                name = profile.getName(),
                description = profile.getDescription(),
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
    user: User,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val profiles = user.getRelatedOrganizations()
        for (profile in profiles) {
            ProfileSlot(
                name = profile.getName(),
                description = profile.getDescription(),
                modifier = Modifier.height(64.dp)
            )
        }
    }
}