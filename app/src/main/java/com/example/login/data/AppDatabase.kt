package com.example.login.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import java.util.Date

// 1. ADD Reservation::class and BUMP the version to 3
@Database(entities = [User::class, Author::class, Book::class, Loan::class, Reservation::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun authorDao(): AuthorDao
    abstract fun bookDao(): BookDao
    abstract fun loanDao(): LoanDao
    abstract fun reservationDao(): ReservationDao // <-- 2. ADD the new DAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "biblionet_database"
                )
                .fallbackToDestructiveMigration() // This handles the version update for now
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

// Converters class remains the same
class Converters {
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @androidx.room.TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}