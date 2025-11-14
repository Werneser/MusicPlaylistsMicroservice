package org.example.repository

import org.example.entity.Playlist
import org.springframework.data.repository.CrudRepository

interface PlaylistRepository: CrudRepository<Playlist, Long> {
    fun findAllByOwnerId( ownerId: Long): List<Playlist>

}