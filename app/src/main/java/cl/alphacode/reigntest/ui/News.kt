package cl.alphacode.reigntest.ui

import java.util.Date

data class NewsUi (
    var title: String,
    var author: String,
    var createdAt: Date,
    var createdAtI: Int,
    var storyUrl: String,
)