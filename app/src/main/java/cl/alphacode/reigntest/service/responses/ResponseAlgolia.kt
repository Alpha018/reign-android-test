package cl.alphacode.reigntest.service.algolia.responses

import com.google.gson.annotations.SerializedName
import java.util.*

data class AlgoliaResponse(
    var hits: List<Hits>,
)

data class Hits(
    var title: String?,
    var author: String,
    @SerializedName("created_at")
    var createdAt: Date,
    @SerializedName("created_at_i")
    var createdAtI: Int,
    @SerializedName("story_title")
    var storyTitle: String?,
    @SerializedName("story_url")
    var storyUrl: String?,
)