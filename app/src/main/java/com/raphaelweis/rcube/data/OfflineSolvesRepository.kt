package com.raphaelweis.rcube.data

import kotlinx.coroutines.flow.Flow

class OfflineSolvesRepository(private val solvesDAO: SolvesDAO) : SolvesRepository {
    override fun getAllSolvesStream(): Flow<List<Solve>> = solvesDAO.getAllSolves()

    override fun getSolveStream(id: Int): Flow<Solve?> = solvesDAO.getSolve(id)

    override suspend fun insertSolve(solve: Solve) = solvesDAO.insert(solve)

    override suspend fun deleteSolve(id: Int) = solvesDAO.delete(id)

    override suspend fun updateSolve(solve: Solve) = solvesDAO.update(solve)
}