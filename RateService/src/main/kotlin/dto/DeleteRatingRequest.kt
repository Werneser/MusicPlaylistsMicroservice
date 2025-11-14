package org.example.dto

data class DeleteRatingRequest(
    val trackId: Long,
    val userId: Long
)
