package cl.alphacode.reigntest.ui.organism

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.alphacode.reigntest.service.algolia.responses.Hits
import cl.alphacode.reigntest.ui.molecule.SubTitleData
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


@Composable
fun CardComponent(hits: Hits) {
    val prettyTime = PrettyTime(Locale.getDefault())
    val ago: String = prettyTime.format(hits.createdAt)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = hits.title ?: hits.storyTitle ?: "",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        SubTitleData(hits.author, ago)
    }
}