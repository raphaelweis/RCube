package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.entities.Solve
import kotlinx.coroutines.flow.Flow

interface SolvesRepository {
    fun getAllSolvesStream(): Flow<List<Solve>>

    fun getSolveStream(id: Int): Flow<Solve?>

    suspend fun insertSolve(solve: Solve)

    suspend fun deleteSolve(id: Int)

    suspend fun updateSolve(solve: Solve)
}