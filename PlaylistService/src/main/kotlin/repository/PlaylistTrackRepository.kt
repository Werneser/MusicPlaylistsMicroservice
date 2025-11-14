package org.example.repository

import org.example.entity.Playlist
import org.example.entity.PlaylistTrack
import org.example.entity.PlaylistTrackId
import org.springframework.data.repository.CrudRepository

interface PlaylistTrackRepository: CrudRepository<PlaylistTrack, PlaylistTrackId> {
    fun findAllByIdPlaylistId(playlistId: Long): List<PlaylistTrack>
}