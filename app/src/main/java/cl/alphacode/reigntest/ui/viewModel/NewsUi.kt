package cl.alphacode.reigntest.ui.viewModel

import java.util.*

data class NewsUi(
    var title: String,
    var author: String,
    var createdAt: Date,
    var createdAtI: Int,
    var storyUrl: String,
)
