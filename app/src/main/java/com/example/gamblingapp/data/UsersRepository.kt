package com.example.gamblingapp.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    suspend fun insert(user: User)

    suspend fun update(user: User)

    suspend fun delete(user: User)

    fun getUserStream(email: String, password: String): Flow<User>

    fun getUserEmailStream(email: String): Flow<User>

    fun getUserPeselStream(pesel: String): Flow<User>
}