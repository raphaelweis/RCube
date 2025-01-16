package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.entities.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    fun getUserStream(id: Long): Flow<User?>

    suspend fun insertUser(user: User): Long

    suspend fun deleteUser(id: Long)

    suspend fun updateUser(user: User)
}