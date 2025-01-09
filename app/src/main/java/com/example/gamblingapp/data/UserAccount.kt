package com.example.gamblingapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    val fullName: String,
    val balance: Float,
    val password: String,
    @PrimaryKey
    val email: String,
    val birthDate: String,
    val pesel: String
    )