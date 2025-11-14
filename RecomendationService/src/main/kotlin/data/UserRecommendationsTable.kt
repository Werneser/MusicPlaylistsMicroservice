package data
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object UserRecommendationsTable : IntIdTable("user_recs") {
    val userId = long("user_id")
    val recGenre = varchar("rec_genre", 50)
    val recommendationJson = varchar("recommendation_json", length = 1000)
}

class UserRecommendationsDAO(id: EntityID<Int>): IntEntity(id){
    companion object: IntEntityClass<UserRecommendationsDAO>(UserRecommendationsTable)
    var userId: Long by UserRecommendationsTable.userId
    var recGenre by UserRecommendationsTable.recGenre
    var recommendationJson by UserRecommendationsTable.recommendationJson
}