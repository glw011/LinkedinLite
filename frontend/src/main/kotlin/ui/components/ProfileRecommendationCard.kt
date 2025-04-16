package ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

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

@Composable
fun PersonalProfileRecommendationCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {}
}

@Composable
fun OrganizationProfileRecommendationCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {}
}