package org.example.service

import org.example.client.MusicApiClient
import org.example.client.ProfileApiClient
import org.example.client.RecApiClient
import org.example.dto.AllPlaylistRespond
import org.example.dto.PlaylistDTO
import org.example.dto.PlaylistRespond
import org.example.entity.Playlist
import org.example.entity.PlaylistTrack
import org.example.entity.PlaylistTrackId
import org.example.repository.PlaylistRepository
import org.example.repository.PlaylistTrackRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PlaylistService(
    private val playlistRepository: PlaylistRepository,
    private  val playlistTrackRepository: PlaylistTrackRepository,
    private val musicApiClient: MusicApiClient,
    private val profileApiClient: ProfileApiClient,
    private val recApiClient: RecApiClient
) {

    fun getPlaylistWithTracks(playlistId: Long): PlaylistRespond? {
        val playlistOpt = playlistRepository.findById(playlistId)
        if (playlistOpt.isEmpty) return null

        val playlistMetadata = playlistOpt.get()

        val authorName = profileApiClient.getProfileNameById(playlistMetadata.ownerId)?: "unnamed"
        val playlistTracks = playlistTrackRepository.findAllByIdPlaylistId(playlistId)

        val trackIds = playlistTracks.map { it.id.trackId }

        val tracks = trackIds.mapNotNull { trackId ->
            musicApiClient.getTrackById(trackId)
        }

        return PlaylistRespond(
            id = playlistMetadata.id,
            title = playlistMetadata.title,
            owner = authorName,
            tracks = tracks,
            updatedAt = playlistMetadata.updatedAt
        )
    }

    fun getRecommendationPlayList(userId: Long): PlaylistRespond?{
        val playlistOpt = playlistRepository.findById(1)
        val recPlaylist = playlistOpt.get()
        recApiClient.analyze(userId)?: return null

        val recommendationIds = recApiClient.getRecommendations(userId)?.recommendationsIdsList?: return null

        val tracks = recommendationIds.mapNotNull { trackId ->
            musicApiClient.getTrackById(trackId)
        }

        return PlaylistRespond(
            id = recPlaylist.id,
            title = recPlaylist.title,
            owner = "MusicPlaylistsApp",
            tracks = tracks,
            updatedAt = LocalDateTime.now()
        )
    }

    fun getAllPlaylists(userId: Long): AllPlaylistRespond{
        val playlistsList = playlistRepository.findAllByOwnerId(userId)
        return AllPlaylistRespond(playlistsList.map { it -> PlaylistDTO(it.id,it.title) })
    }

    fun getPlaylistMetadataById(playlistId: Long): Playlist? {
        val playlistOpt = playlistRepository.findById(playlistId)
        if (playlistOpt.isEmpty) return null

        val playlistMetadata = playlistOpt.get()

        return playlistMetadata
    }


    fun updatePlaylist(id: Long, newTitle: String?, newTrackIds: List<Long>?,userId: Long): Boolean {
        val playlistOpt = playlistRepository.findById(id)
        if (playlistOpt.isEmpty) {
            return false
        }
        val playlist = playlistOpt.get()

        if (playlist.ownerId!=userId) return false

        // Обновляем название
        newTitle?.let {
            playlist.title = it
            playlistRepository.save(playlist)
        }
        newTrackIds?.let {
            // Удаляем старые связи треков
            val existingTracks = playlistTrackRepository.findAllByIdPlaylistId(id)
            playlistTrackRepository.deleteAll(existingTracks)

            // Создаем новые связи
            it.forEachIndexed { position, trackId ->
                val playlistTrack = PlaylistTrack(
                    id = PlaylistTrackId(playlistId = id, trackId = trackId),
                    position = position
                )
                playlistTrackRepository.save(playlistTrack)
            }
        }
        return true
    }


    fun createPlaylist( title: String, ownerId: Long, tracksIds: List<Long> ): Long?{
        val playlist = Playlist(
            title = title,
            ownerId = ownerId,
        )
        val saved = playlistRepository.save(playlist)
        saved.id?.let { playlistId ->
            tracksIds.mapIndexed { position, trackid ->
                val track = PlaylistTrack(
                    id = PlaylistTrackId(playlistId = playlistId, trackid),
                    position = position
                )
                playlistTrackRepository.save(track)
            }
        }
        return saved.id
    }
}