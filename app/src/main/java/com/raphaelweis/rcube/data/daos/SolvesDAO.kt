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
    suspend fun delete(id: Long)

    @Query("SELECT * FROM solves WHERE id = :id")
    fun getSolve(id: Long): Flow<Solve>

    @Query("SELECT * FROM solves ORDER BY date DESC")
    fun getAllSolves(): Flow<List<Solve>>

    @Query("SELECT time FROM solves ORDER BY date DESC LIMIT :count")
    fun getLastXSolves(count: Int): Flow<List<Long>>

    @Query("SELECT time FROM solves ORDER BY time ASC LIMIT 1")
    fun getBestSolveTime(): Flow<Long>

    @Query("SELECT AVG(time) FROM solves")
    fun getAverageSolveTime(): Flow<Long>

    @Query("SELECT COUNT(*) FROM solves")
    fun getTotalSolveCount(): Flow<Int>
}