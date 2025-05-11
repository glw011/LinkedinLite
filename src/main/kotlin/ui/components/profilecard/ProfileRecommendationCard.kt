package ui.components.profilecard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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