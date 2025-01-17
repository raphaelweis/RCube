package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.entities.Solve
import kotlinx.coroutines.flow.Flow

interface SolvesRepository {
    fun getAllSolvesStream(): Flow<List<Solve>>

    fun getSolveStream(id: Long): Flow<Solve?>

    suspend fun insertSolve(solve: Solve)

    suspend fun deleteSolve(id: Long)

    suspend fun updateSolve(solve: Solve)
}