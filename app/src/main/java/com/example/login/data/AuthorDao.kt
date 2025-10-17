package com.example.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthorDao {
    @Insert
    suspend fun insertAuthor(author: Author)

    // --- ADD THIS FUNCTION ---
    // It counts the number of rows in the 'authors' table.
    @Query("SELECT COUNT(*) FROM authors")
    suspend fun getAuthorCount(): Int
}