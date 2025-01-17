package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.entities.Solve
import kotlinx.coroutines.flow.Flow

interface SolvesRepository {
    fun getAllSolvesStream(): Flow<List<Solve>>

    fun getSolveStream(id: Long): Flow<Solve?>

    suspend fun insertSolve(solve: Solve): Long

    suspend fun deleteSolve(id: Long)

    suspend fun updateSolve(solve: Solve)

    suspend fun getLastXSolvesStream(count: Int): Flow<List<Long>>

    suspend fun getBestSolve(): Flow<Long>

    suspend fun getAverageSolveTime(): Flow<Long>

    suspend fun getTotalSolveCount(): Flow<Int>
}