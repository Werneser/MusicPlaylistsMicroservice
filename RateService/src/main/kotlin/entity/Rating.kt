package org.example.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.time.LocalDateTime

@Entity
@Table(name = "rating",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "track_id"])])
data class Rating(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "track_id", nullable = false)
    val trackId: Long,

    @Column(nullable = false)
    val stars: Int,

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null


)
