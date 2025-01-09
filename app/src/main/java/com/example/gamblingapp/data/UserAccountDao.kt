package com.example.gamblingapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * from users WHERE email = :email AND password = :password")
    fun getItem(email: String, password: String): Flow<User>

    @Query("SELECT * from users WHERE email = :email")
    fun getItemEmail(email: String): Flow<User>

    @Query("SELECT * from users WHERE pesel = :pesel")
    fun getItemPesel(pesel: String): Flow<User>
}