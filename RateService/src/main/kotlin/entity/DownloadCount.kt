package org.example.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "download_count")
data class DownloadCount(
    @Id
    @Column(name = "track_id")
    val trackId: Long,

    @Column(name = "download_count")
    val downloadCount: Long = 0,
)
