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
import cl.alphacode.reigntest.entity.News
import cl.alphacode.reigntest.ui.molecule.SubTitleData
import cl.alphacode.reigntest.ui.viewModel.NewsUi
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


@Composable
fun CardComponent(news: News) {
    val prettyTime = PrettyTime(Locale.getDefault())
    val ago: String = prettyTime.format(news.createdAt)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = news.title,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        SubTitleData(news.author, ago)
    }
}