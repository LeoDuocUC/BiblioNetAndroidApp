package com.example.login.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,
    @ColumnInfo(name = "full_name")
    val fullName: String,
    val email: String,
    @ColumnInfo(name = "password_hash")
    val passwordHash: String
)