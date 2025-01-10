package com.raphaelweis.rcube.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SolvesDAO {
    @Insert
    suspend fun insert(solve: Solve)

    @Update
    suspend fun update(solve: Solve)

    @Delete
    suspend fun delete(solve: Solve)

    @Query("SELECT * from solves WHERE id = :id")
    fun getSolve(id: Int): Flow<Solve>

    @Query("SELECT * from solves")
    fun getAllSolves(): Flow<List<Solve>>
}