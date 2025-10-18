package com.example.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login.data.Book
import com.example.login.data.BookDao
import com.example.login.data.Loan
import com.example.login.data.LoanDao
import com.example.login.data.LoanWithBookAndAuthor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Date

// THE VIEWMODEL NOW NEEDS BOTH DAOs
class MyLoansViewModel(
    private val loanDao: LoanDao,
    private val bookDao: BookDao
) : ViewModel() {

    // This part remains the same
    val userLoans: StateFlow<List<LoanWithBookAndAuthor>> = loanDao.getLoansForUser(1)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    // --- ADD THIS NEW FUNCTION ---
    // Handles the logic for returning a book
    fun returnBook(loan: Loan, book: Book) {
        viewModelScope.launch {
            // 1. Update the loan with the current date as the return date
            val updatedLoan = loan.copy(returnDate = Date())
            loanDao.updateLoan(updatedLoan)

            // 2. Update the book's state back to "Available"
            val updatedBook = book.copy(state = "Available")
            bookDao.updateBook(updatedBook)
        }
    }
}

// THE FACTORY NOW NEEDS BOTH DAOs
class MyLoansViewModelFactory(
    private val loanDao: LoanDao,
    private val bookDao: BookDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyLoansViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyLoansViewModel(loanDao, bookDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}