package com.example.gamblingapp.data

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

class UsersRepository(private val db : GamblingDatabase) {
    suspend fun insertUser(user: User)
    {
        db.userDao.insert(user)
    }

    suspend fun updateUser(user: User)
    {
        db.userDao.update(user)
    }

    suspend fun deleteUser(user: User)
    {
        db.userDao.delete(user)
    }

    fun getUserStream(email: String, password: String): Flow<User> = db.userDao.getUser(email,password)

    fun getUserEmailStream(email: String): Flow<User> = db.userDao.getUserEmail(email)

    fun getUserPeselStream(pesel: String): Flow<User> = db.userDao.getUserPesel(pesel)

    suspend fun doesUserExist(email: String, password: String) = db.userDao.doesUserExist(email, password)

    suspend fun doesUserEmailExist(email: String) = db.userDao.doesUserEmailExist(email)

    suspend fun doesUserPeselExist(pesel: String) = db.userDao.doesUserPeselExist(pesel)
}