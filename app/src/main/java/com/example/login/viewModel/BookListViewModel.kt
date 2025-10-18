package com.example.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

// This enum represents the different states of the reservation UI
enum class ReservationState {
    IDLE,
    LOADING,
    SUCCESS,
    ERROR_LIMIT_REACHED,
    ERROR_ALREADY_RESERVED
}

class BookListViewModel(
    private val bookDao: BookDao,
    private val loanDao: LoanDao,
    private val reservationDao: ReservationDao
) : ViewModel() {

    private val _reservationState = MutableStateFlow(ReservationState.IDLE)
    val reservationState = _reservationState.asStateFlow()

    val books: StateFlow<List<BookWithAuthor>> = bookDao.getAllBooksWithAuthors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _selectedBook = MutableStateFlow<BookWithAuthor?>(null)
    val selectedBook = _selectedBook.asStateFlow()

    fun findBookById(bookId: Int) {
        viewModelScope.launch {
            _selectedBook.value = bookDao.getBookById(bookId)
        }
    }

    fun requestLoan(book: Book, userId: Int) {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            val loanDate = calendar.time
            calendar.add(Calendar.DAY_OF_YEAR, 14) // Due in 14 days
            val dueDate = calendar.time

            val newLoan = Loan(userId = userId, bookId = book.bookId, loanDate = loanDate, dueDate = dueDate)
            loanDao.insertLoan(newLoan)

            val updatedBook = book.copy(state = "Loaned")
            bookDao.updateBook(updatedBook)

            findBookById(book.bookId)
        }
    }

    fun reserveBook(book: Book, userId: Int) {
        // Only start a new reservation if we are not already in the middle of one
        if (_reservationState.value != ReservationState.LOADING) {
            viewModelScope.launch {
                _reservationState.value = ReservationState.LOADING

                if (reservationDao.countReservationsByUser(userId) >= 5) {
                    _reservationState.value = ReservationState.ERROR_LIMIT_REACHED
                    return@launch
                }

                if (reservationDao.hasReservationForBook(userId, book.bookId) > 0) {
                    _reservationState.value = ReservationState.ERROR_ALREADY_RESERVED
                    return@launch
                }

                val newReservation = Reservation(userId = userId, bookId = book.bookId, reservationDate = Date())
                reservationDao.insertReservation(newReservation)
                _reservationState.value = ReservationState.SUCCESS
            }
        }
    }

    fun resetReservationState() {
        _reservationState.value = ReservationState.IDLE
    }
}

class BookListViewModelFactory(
    private val bookDao: BookDao,
    private val loanDao: LoanDao,
    private val reservationDao: ReservationDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookListViewModel(bookDao, loanDao, reservationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}