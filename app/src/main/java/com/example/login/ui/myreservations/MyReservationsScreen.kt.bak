package com.example.login.ui.myloans

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.login.data.Book
import com.example.login.data.LoanWithBookAndAuthor
import com.example.login.ui.booklist.BookItem

@Composable
fun MyLoansScreen(loans: List<LoanWithBookAndAuthor>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // This is the function that needed the import
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(loans) { loan ->
            // Reusing the BookItem composable
            BookItem(
                book = loan.book,
                authorName = loan.authorName,
                onClick = {
                    // Navigate to the detail screen for the loaned book
                    navController.navigate("bookdetail/${loan.book.bookId}")
                }
            )
        }
    }
}