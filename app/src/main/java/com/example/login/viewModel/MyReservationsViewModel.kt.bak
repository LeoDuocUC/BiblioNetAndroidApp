package com.example.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login.data.LoanDao
import com.example.login.data.LoanWithBookAndAuthor
import kotlinx.coroutines.flow.*

class MyLoansViewModel(loanDao: LoanDao) : ViewModel() {
    // Por ahora, usamos un ID de usuario fijo (1) para el "Test User".
    val userLoans: StateFlow<List<LoanWithBookAndAuthor>> = loanDao.getLoansForUser(1)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
}

class MyLoansViewModelFactory(private val loanDao: LoanDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyLoansViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyLoansViewModel(loanDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}