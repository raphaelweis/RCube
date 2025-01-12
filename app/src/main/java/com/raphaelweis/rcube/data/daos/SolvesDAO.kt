package com.raphaelweis.rcube.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raphaelweis.rcube.data.entities.Solve
import kotlinx.coroutines.flow.Flow

@Dao
interface SolvesDAO {
    @Insert
    suspend fun insert(solve: Solve)

    @Update
    suspend fun update(solve: Solve)

    @Query("DELETE FROM solves WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("SELECT * from solves WHERE id = :id")
    fun getSolve(id: Int): Flow<Solve>

    @Query("SELECT * from solves ORDER BY date DESC")
    fun getAllSolves(): Flow<List<Solve>>
}