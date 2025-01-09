package com.example.gamblingapp.data

import androidx.room.Database
import androidx.room.RoomDatabase


//Database class with a singleton Instance object.
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GamblingDatabase : RoomDatabase() {
    abstract val userDao: UserDao
}