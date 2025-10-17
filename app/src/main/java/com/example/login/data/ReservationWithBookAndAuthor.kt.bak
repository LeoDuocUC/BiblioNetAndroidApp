package com.example.login.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "reservations",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["userId"], childColumns = ["userId"]),
        ForeignKey(entity = Book::class, parentColumns = ["bookId"], childColumns = ["bookId"])
    ],
    indices = [Index(value = ["userId"]), Index(value = ["bookId"])] // <-- ADDED FOR INDEXING
)
data class Reservation(
    @PrimaryKey(autoGenerate = true)
    val reservationId: Int = 0,
    val userId: Int,
    val bookId: Int,
    val reservationDate: Date
)