package com.example.login.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "loans",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"]),
        ForeignKey(entity = Book::class, parentColumns = ["bookId"], childColumns = ["bookId"])
    ],
    indices = [Index(value = ["userId"]), Index(value = ["bookId"])] // <-- ADDED FOR INDEXING
)
data class Loan(
    @PrimaryKey(autoGenerate = true)
    val loanId: Int = 0,
    val userId: Int,
    val bookId: Int,
    val loanDate: Date,
    val dueDate: Date
)