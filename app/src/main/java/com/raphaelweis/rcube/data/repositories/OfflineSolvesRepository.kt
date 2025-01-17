package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.entities.Solve
import com.raphaelweis.rcube.data.daos.SolvesDAO
import kotlinx.coroutines.flow.Flow

class OfflineSolvesRepository(private val solvesDAO: SolvesDAO) : SolvesRepository {
    override fun getAllSolvesStream(): Flow<List<Solve>> = solvesDAO.getAllSolves()

    override fun getSolveStream(id: Long): Flow<Solve?> = solvesDAO.getSolve(id)

    override suspend fun insertSolve(solve: Solve): Long = solvesDAO.insert(solve)

    override suspend fun deleteSolve(id: Long) = solvesDAO.delete(id)

    override suspend fun updateSolve(solve: Solve) = solvesDAO.update(solve)

    override suspend fun getLastXSolvesStream(count: Int): Flow<List<Long>> =
        solvesDAO.getLastXSolves(count)

    override suspend fun getBestSolve(): Flow<Long> = solvesDAO.getBestSolveTime()

    override suspend fun getAverageSolveTime(): Flow<Long> = solvesDAO.getAverageSolveTime()

    override suspend fun getTotalSolveCount(): Flow<Int> = solvesDAO.getTotalSolveCount()
}