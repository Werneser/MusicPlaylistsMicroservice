package org.example.dto

data class UpdatePlaylistRequest(
    val title: String?,
    val trackIds: List<Long>?
)
