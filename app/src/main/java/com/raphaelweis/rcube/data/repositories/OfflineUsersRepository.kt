package com.raphaelweis.rcube.data.repositories

import com.raphaelweis.rcube.data.daos.UsersDAO
import com.raphaelweis.rcube.data.entities.User
import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val usersDAO: UsersDAO) : UsersRepository {
    override fun getUserStream(id: Long): Flow<User?> = usersDAO.getUser(id)

    override suspend fun insertUser(user: User) = usersDAO.insert(user)

    override suspend fun deleteUser(id: Long) = usersDAO.delete(id)

    override suspend fun updateUser(user: User) = usersDAO.update(user)
}