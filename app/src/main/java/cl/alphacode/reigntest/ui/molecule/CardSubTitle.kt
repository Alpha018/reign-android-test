package cl.alphacode.reigntest.ui.molecule

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cl.alphacode.reigntest.ui.atoms.TextSubTitle

@Composable
fun SubTitleData(author: String, time: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        TextSubTitle(text = author)
        TextSubTitle(text = " - ")
        TextSubTitle(text = time)
    }
}