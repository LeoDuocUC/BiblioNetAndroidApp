package com.example.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update // <-- Add this import
import kotlinx.coroutines.flow.Flow

@Dao
interface LoanDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLoan(loan: Loan)
    
    // --- ADD THIS FUNCTION ---
    @Update
    suspend fun updateLoan(loan: Loan)
    // -------------------------

    // --- THIS QUERY IS NOW IMPROVED ---
    // It will only get loans that have not been returned yet.
    @Query("""
        SELECT l.*,
               b.bookId AS book_bookId, b.title AS book_title, b.authorId AS book_authorId,
               b.isbn AS book_isbn, b.genre AS book_genre, b.coverUrl AS book_coverUrl,
               b.state AS book_state, a.fullName AS authorName
        FROM loans AS l
        JOIN books AS b ON l.bookId = b.bookId
        JOIN authors AS a ON b.authorId = a.authorId
        WHERE l.userId = :userId AND l.returnDate IS NULL
    """)
    fun getLoansForUser(userId: Int): Flow<List<LoanWithBookAndAuthor>>
}