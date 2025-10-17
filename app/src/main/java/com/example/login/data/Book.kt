package com.example.login.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "books",
    foreignKeys = [ForeignKey(
        entity = Author::class,
        parentColumns = ["authorId"],
        childColumns = ["authorId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["authorId"])] // <-- ADDED FOR INDEXING
)
data class Book(
    @PrimaryKey(autoGenerate = true)
    val bookId: Int = 0,
    val title: String,
    val authorId: Int,
    val isbn: String,
    val genre: String,
    val coverUrl: String,
    val state: String
)