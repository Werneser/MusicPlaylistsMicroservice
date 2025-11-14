package data

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationsRespond(
    val recommendationsIdsList: List<Long>
)