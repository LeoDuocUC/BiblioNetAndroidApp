package com.example.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Insert
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Query("""
        SELECT b.*, a.fullName as authorName
        FROM books AS b
        JOIN authors AS a ON b.authorId = a.authorId
    """)
    fun getAllBooksWithAuthors(): Flow<List<BookWithAuthor>>

    @Query("""
        SELECT b.*, a.fullName as authorName
        FROM books AS b
        JOIN authors AS a ON b.authorId = a.authorId
        WHERE b.bookId = :bookId
    """)
    suspend fun getBookById(bookId: Int): BookWithAuthor?
}