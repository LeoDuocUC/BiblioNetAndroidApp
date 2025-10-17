package com.example.login.ui.myreservations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login.data.ReservationWithBookAndAuthor
import com.example.login.ui.booklist.BookItem

@Composable
fun MyReservationsScreen(reservations: List<ReservationWithBookAndAuthor>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(reservations) { reservation ->
            BookItem(
                book = reservation.book,
                authorName = reservation.authorName,
                onClick = {
                    navController.navigate("bookdetail/${reservation.book.bookId}")
                }
            )
        }
    }
}