package com.example.login.data

import androidx.room.Embedded

// This class holds the combined result of our database query
data class BookWithAuthor(
    @Embedded val book: Book,
    val authorName: String
)