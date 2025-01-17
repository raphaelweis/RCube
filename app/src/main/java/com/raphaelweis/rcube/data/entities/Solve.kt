package com.raphaelweis.rcube.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "solves", foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Solve(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val time: Long,
    val date: Long,
    val scramble: String,
    val plusTwo: Boolean,
    val dnf: Boolean,
    val userId: Long?,
)