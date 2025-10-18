package com.example.login.ui.myreservations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login.data.ReservationWithBookAndAuthor
import com.example.login.viewModel.MyReservationsViewModel

@Composable
fun MyReservationsScreen(
    reservations: List<ReservationWithBookAndAuthor>,
    navController: NavController,
    viewModel: MyReservationsViewModel
) {
    // Check if the list of reservations is empty
    if (reservations.isEmpty()) {
        // If it's empty, show a message in the center of the screen
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No tiene Reservas.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        // If it's not empty, show the list of reserved books
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reservations) { reservation ->
                ReservationItem(
                    reservation = reservation,
                    onCancel = {
                        viewModel.cancelReservation(reservation.reservation)
                    }
                )
            }
        }
    }
}