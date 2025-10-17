package com.example.login.data

import androidx.room.Embedded

data class LoanWithBookAndAuthor(
    @Embedded val loan: Loan,
    @Embedded(prefix = "book_") // <-- ADD THIS PREFIX
    val book: Book,
    val authorName: String
)