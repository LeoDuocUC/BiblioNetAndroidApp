package com.example.login.ui.booklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.login.data.Book
import com.example.login.data.BookWithAuthor
import com.example.login.ui.theme.LoginTheme

@Composable
fun BookListScreen(books: List<BookWithAuthor>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(books) { bookWithAuthor ->
            // CAMBIO: Ahora pasamos 'book' y 'authorName' por separado.
            BookItem(
                book = bookWithAuthor.book,
                authorName = bookWithAuthor.authorName,
                onClick = {
                    navController.navigate("bookdetail/${bookWithAuthor.book.bookId}")
                }
            )
        }
    }
}

// CAMBIO: La firma de la función ahora es más genérica.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(book: Book, authorName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column {
                // CAMBIO: Usamos los nuevos parámetros.
                Text(text = book.title, style = MaterialTheme.typography.titleLarge)
                Text(text = authorName, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookListScreenPreview() {
    LoginTheme {
        val sampleBooks = listOf(
            BookWithAuthor(
                book = Book(1, "The Lord of the Rings", 1, "978-0618640157", "Fantasy", "", "Available"),
                authorName = "J.R.R. Tolkien"
            ),
            BookWithAuthor(
                book = Book(2, "Cien Años de Soledad", 2, "978-0307350442", "Magic Realism", "", "Loaned"),
                authorName = "Gabriel García Márquez"
            )
        )
        BookListScreen(books = sampleBooks, navController = rememberNavController())
    }
}