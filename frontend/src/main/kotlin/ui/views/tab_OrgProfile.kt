package ui.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.components.ProfileHeader

val headerShape = RoundedCornerShape(16.dp)

@Composable
fun OrgProfileTab() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(8.dp)
            .border(2.dp, Color.Gray, headerShape)
            .clip(headerShape)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            name = "Organization Name",
            description = "Organization Description",
            title = "Organization Title",
            location = "Organization Location",
            school = "Organization School",
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
        )
    }
//    Profile(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
////        ProfileCard(
////            title = "About Us",
////            subtitle = "Learn more about us",
////            content = {
////                Text(text = "This is the about us section.")
////            },
////            modifier = Modifier
////                .fillMaxWidth()
////                .wrapContentHeight()
////        )
//    }
}