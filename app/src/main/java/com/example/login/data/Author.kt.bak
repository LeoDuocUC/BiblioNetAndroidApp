package com.example.login.data // This will be the correct package

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    // Function to insert a new user
    @Insert
    suspend fun insertUser(user: User)

    // Function to find a user by their email
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun findUserByEmail(email: String): User?
}