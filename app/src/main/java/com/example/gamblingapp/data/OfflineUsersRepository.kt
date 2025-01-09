package com.example.gamblingapp.data

import kotlinx.coroutines.flow.Flow

class OfflineUsersRepository(private val userDao: UserDao) : UsersRepository {

    override suspend fun insert(user: User) = userDao.insert(user)

    override suspend fun update(user: User) = userDao.insert(user)

    override suspend fun delete(user: User) = userDao.insert(user)

    override fun getUserStream(email: String, password: String): Flow<User> = userDao.getItem(email, password)

    override fun getUserEmailStream(email: String): Flow<User> = userDao.getItemEmail(email)

    override fun getUserPeselStream(pesel: String): Flow<User> = userDao.getItemPesel(pesel)
}