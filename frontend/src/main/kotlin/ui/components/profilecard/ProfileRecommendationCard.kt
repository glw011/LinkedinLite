package ui.components.profilecard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ui.components.image.PfpImage

/**
 * Creates a slot for a profile to be used in a recommendation card.
 *
 * @param modifier The modifier for the profile slot.
 */
@Composable
fun ProfileSlot(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        PfpImage(
            imageBitmap = null,
        )
        Column(
            modifier = Modifier
        ) {
            Text(
                text = "Name",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = "x Mutual Friends",
                style = MaterialTheme.typography.bodySmall,
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
    ) {}
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
    ) {}
}