package org.example.entity

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "playlist_track")
data class PlaylistTrack(
    @EmbeddedId
    val id: PlaylistTrackId,

    @Column(name = "position")
    val position: Int? = null
)