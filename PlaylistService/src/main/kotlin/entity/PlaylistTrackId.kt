package org.example.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class PlaylistTrackId(
    @Column(name = "playlist_id")
    val playlistId: Long,

    @Column(name = "track_id")
    val trackId: Long
)