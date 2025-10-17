package com.example.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReservationDao {
    @Insert
    suspend fun insertReservation(reservation: Reservation)

    // --- ADD THIS NEW FUNCTION ---
    @Query("""
        SELECT r.*,
               b.bookId AS book_bookId,
               b.title AS book_title,
               b.authorId AS book_authorId,
               b.isbn AS book_isbn,
               b.genre AS book_genre,
               b.coverUrl AS book_coverUrl,
               b.state AS book_state,
               a.fullName AS authorName
        FROM reservations AS r
        JOIN books AS b ON r.bookId = b.bookId
        JOIN authors AS a ON b.authorId = a.authorId
        WHERE r.userId = :userId
    """)
    fun getReservationsForUser(userId: Int): Flow<List<ReservationWithBookAndAuthor>>
}