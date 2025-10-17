package com.example.login.data

import androidx.room.Embedded

data class ReservationWithBookAndAuthor(
    @Embedded val reservation: Reservation,
    @Embedded(prefix = "book_")
    val book: Book,
    val authorName: String
)