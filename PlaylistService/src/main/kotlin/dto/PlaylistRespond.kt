package org.example.dto

import java.time.LocalDateTime

data class PlaylistRespond(
    val id: Long?,
    val title: String,
    val owner: String,
    val tracks: List<TrackMetadataResponse>,
    val updatedAt: LocalDateTime
)
