package com.raphaelweis.rcube.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.raphaelweis.rcube.data.entities.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDAO {
    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun update(user: User)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Long): Flow<User>
}