package com.example.gamblingapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


//Database class with a singleton Instance object.
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class GamblingDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var Instance: GamblingDatabase? = null

        fun getDatabase(context: Context): GamblingDatabase {
            //if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, GamblingDatabase::class.java, "user_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}