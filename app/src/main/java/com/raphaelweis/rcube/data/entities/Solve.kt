package com.raphaelweis.rcube.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "solves")
data class Solve(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: Long,
    val date: Long,
    val scramble: String
)