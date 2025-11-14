package data
import kotlinx.serialization.Serializable


@Serializable
data class UserRecommendation(
    val userId: Long,
    val genre: String)
