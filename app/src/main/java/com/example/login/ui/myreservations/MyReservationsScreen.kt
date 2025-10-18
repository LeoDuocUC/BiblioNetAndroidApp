package com.example.login.ui.myreservations

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
    viewModel: MyReservationsViewModel // Pass the ViewModel to the screen
) {
    if (reservations.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No tiene Reservas.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(reservations) { reservation ->
                // Call the new ReservationItem composable
                ReservationItem(
                    reservation = reservation,
                    onCancel = {
                        // Call the cancelReservation function from the ViewModel
                        viewModel.cancelReservation(reservation.reservation)
                    }
                )
            }
        }
    }
}

// --- THIS IS THE MISSING COMPOSABLE ---
@Composable
fun ReservationItem(reservation: ReservationWithBookAndAuthor, onCancel: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = reservation.book.title, style = MaterialTheme.typography.titleLarge)
            Text(text = reservation.authorName, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onCancel,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Cancelar Reserva")
            }
        }
    }
}