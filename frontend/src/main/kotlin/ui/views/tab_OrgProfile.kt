package ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.skia.Surface
import ui.components.ProfileCard
import ui.components.ProfileHeader
import ui.components.ProfilePostsCard

val headerShape = RoundedCornerShape(16.dp)
val postShape = RoundedCornerShape(8.dp)

@Composable
fun OrgProfileTab() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ProfileHeader(
                    name = "Organization Name",
                    description = "\"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\"",
                    title = "Organization Title",
                    location = "Organization Location",
                    school = "Organization School",
                    modifier = Modifier
                        .wrapContentHeight()
                        .fillMaxWidth()
                        .border(1.dp, Color.Gray, headerShape)
                        .clip(headerShape)
                )
                ProfilePostsCard(
                    modifier = Modifier
                        .height(650.dp)
                        .fillMaxWidth()
                )
                ProfileCard(
                    title = "Tags",
                    subtitle = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()

                ) {

                }
            }
            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp)
                    .border(1.dp, Color.Gray),
            ) {
                // Temporary: To be replaced by ProfileRecommendations
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    color = Color.White
                ) {}
            }
        }
    }
}