package com.example.login.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.login.data.Reservation
import com.example.login.data.ReservationDao
import com.example.login.data.ReservationWithBookAndAuthor
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MyReservationsViewModel(private val reservationDao: ReservationDao) : ViewModel() {

    val userReservations: StateFlow<List<ReservationWithBookAndAuthor>> = reservationDao.getReservationsForUser(1)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    // --- THIS FUNCTION WAS MISSING ---
    fun cancelReservation(reservation: Reservation) {
        viewModelScope.launch {
            reservationDao.deleteReservation(reservation)
        }
    }
    // ---------------------------------
}

class MyReservationsViewModelFactory(private val reservationDao: ReservationDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyReservationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyReservationsViewModel(reservationDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}