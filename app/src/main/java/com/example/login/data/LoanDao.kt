package com.example.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Insert
    suspend fun insertLoan(loan: Loan)

    // --- THIS IS THE CORRECTED QUERY ---
    @Query("""
        SELECT l.*,
               b.bookId AS book_bookId,
               b.title AS book_title,
               b.authorId AS book_authorId,
               b.isbn AS book_isbn,
               b.genre AS book_genre,
               b.coverUrl AS book_coverUrl,
               b.state AS book_state,
               a.fullName AS authorName
        FROM loans AS l
        JOIN books AS b ON l.bookId = b.bookId
        JOIN authors AS a ON b.authorId = a.authorId
        WHERE l.userId = :userId
    """)
    fun getLoansForUser(userId: Int): Flow<List<LoanWithBookAndAuthor>>
}