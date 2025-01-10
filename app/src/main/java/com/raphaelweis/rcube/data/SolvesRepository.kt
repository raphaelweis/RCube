package com.raphaelweis.rcube.data

import kotlinx.coroutines.flow.Flow

interface SolvesRepository {
    fun getAllSolvesStream(): Flow<List<Solve>>

    fun getSolveStream(id: Int): Flow<Solve?>

    suspend fun insertSolve(solve: Solve)

    suspend fun deleteSolve(id: Int)

    suspend fun updateSolve(solve: Solve)
}