package org.example.repository

import org.example.entity.Rating
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface RatingRepository: JpaRepository<Rating, Long> {
    @Query("SELECT AVG(r.stars) FROM Rating r WHERE r.trackId = :trackId")
    fun findAverageRatingByTrackId(@Param("trackId") trackId: Long): Double?

    @Query("SELECT r FROM Rating r WHERE r.userId = :userId AND r.trackId = :trackId")
    fun findRatingByUserIdAndTrackId(@Param("userId") userId: Long, @Param("trackId") trackId: Long): Optional<Rating>

    fun countByTrackId(trackId: Long): Long
}