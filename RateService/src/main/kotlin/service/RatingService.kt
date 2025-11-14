package org.example.service

import org.example.dto.RatingResponse
import org.example.entity.DownloadCount
import org.example.entity.Rating
import org.springframework.stereotype.Service
import org.example.repository.DownloadCountRepository
import org.example.repository.RatingRepository
import java.time.LocalDateTime

@Service
class RatingService(
    private val downloadCountRepository: DownloadCountRepository,
    private val ratingRepository: RatingRepository
) {
    fun setRating(trackId: Long, stars: Int,userId: Long): Long? {
        if (stars < 0 || stars > 10) return null
        val ratingOpt = ratingRepository.findRatingByUserIdAndTrackId(userId,trackId)
        return if (ratingOpt.isEmpty){
            val rating = Rating(userId = userId, trackId = trackId, stars = stars, updatedAt = LocalDateTime.now())
            val downloads = DownloadCount(trackId)
            ratingRepository.save(rating)
            downloadCountRepository.save(downloads)
            rating.id
        }else{
            val rating = ratingOpt.get()
            val newRating = rating.copy(stars = stars, updatedAt = LocalDateTime.now())
            ratingRepository.save(newRating)
            rating.id
        }
    }

    fun deleteRating(trackId: Long,userId: Long): Boolean {
        val ratingOpt = ratingRepository.findRatingByUserIdAndTrackId(userId,trackId)
        if (ratingOpt.isEmpty) return false
        val rating = ratingOpt.get()
        ratingRepository.delete(rating)
        return true
    }

    fun getRatingAvg(trackId: Long): RatingResponse? {
        val avgRating = ratingRepository.findAverageRatingByTrackId(trackId)
        return avgRating?.let {
            val count = ratingRepository.countByTrackId(trackId)
            RatingResponse(trackId = trackId, avg = it, count = count)
        }
    }


    fun registerDownload(trackId: Long): Boolean {
        val downloadCountOpt = downloadCountRepository.findById(trackId)
        if (downloadCountOpt.isEmpty) return false
        val downloadCount = downloadCountOpt.get()
        val increasedCount = downloadCount.downloadCount + 1
        val  increasedDownloadCount = downloadCount.copy(downloadCount = increasedCount)
        downloadCountRepository.save(increasedDownloadCount)
        return true
    }

}