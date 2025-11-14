package data


import kotlinx.serialization.json.Json
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseRepository(db: Database) {
    init {
        transaction(db) {
            SchemaUtils.create(UserRecommendationsTable)
        }
    }

    fun getRecommendationGenre(userId: Long): String? = transaction {
        UserRecommendationsDAO
            .find { UserRecommendationsTable.userId eq userId }
            .firstOrNull()
            ?.recGenre
    }

    fun getRecommendationJson(userId: Long): String?= transaction {
        UserRecommendationsDAO
            .find { UserRecommendationsTable.userId eq userId }
            .firstOrNull()
            ?.recommendationJson
    }

    fun setRecommendationJson(userId: Long, recommendationJson: String)= transaction {
        UserRecommendationsDAO
            .find { UserRecommendationsTable.userId eq userId }
            .firstOrNull()
            ?.let {
                it.recommendationJson = recommendationJson
            }
    }

    fun saveRecommendations(recommendation: UserRecommendation) = transaction {
        UserRecommendationsDAO.find {
            (UserRecommendationsTable.userId eq recommendation.userId)
        }.firstOrNull()?.let {
            it.recGenre = recommendation.genre
        } ?: run {
            UserRecommendationsDAO.new {
                userId = recommendation.userId
                recGenre = recommendation.genre
                recommendationJson = "{}"
            }
        }
    }
}