package com.example.login.ui.myloans

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
import com.example.login.data.LoanWithBookAndAuthor
import com.example.login.viewModel.MyLoansViewModel

@Composable
fun MyLoansScreen(
    loans: List<LoanWithBookAndAuthor>,
    navController: NavController,
    viewModel: MyLoansViewModel // Pass the ViewModel to the screen
) {
    if (loans.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No tiene Préstamos.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(loans) { loan ->
                // Call the new LoanItem composable
                LoanItem(
                    loan = loan,
                    onReturn = {
                        // Call the returnBook function from the ViewModel
                        viewModel.returnBook(loan.loan, loan.book)
                    },
                    onClick = {
                        navController.navigate("bookdetail/${loan.book.bookId}")
                    }
                )
            }
        }
    }
}

// --- THIS IS THE MISSING COMPOSABLE ---
@Composable
fun LoanItem(
    loan: LoanWithBookAndAuthor,
    onReturn: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = loan.book.title, style = MaterialTheme.typography.titleLarge)
            Text(text = loan.authorName, style = MaterialTheme.typography.bodyMedium)
            // You could also display loan.loan.dueDate here
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onReturn,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Devolver")
            }
        }
    }
}