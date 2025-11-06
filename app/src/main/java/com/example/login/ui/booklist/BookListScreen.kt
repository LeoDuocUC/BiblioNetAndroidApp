package com.example.login.ui.booklist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage // <-- 1. IMPORT COIL
import com.example.login.data.Book
import com.example.login.data.BookWithAuthor
import com.example.login.ui.theme.LoginTheme

@Composable
fun BookListScreen(books: List<BookWithAuthor>, navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) // Increased spacing
    ) {
        items(books) { bookWithAuthor ->
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItem(book: Book, authorName: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        // 2. MODIFIED THIS ROW
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // Aligns image and text nicely
        ) {
            // 3. ADD THE IMAGE
            AsyncImage(
                model = book.coverUrl, // Use the URL from your Book object
                contentDescription = "Cover for ${book.title}",
                modifier = Modifier
                    .size(width = 60.dp, height = 90.dp) // Set a fixed size
                    .clip(RoundedCornerShape(4.dp)), // Add rounded corners
                contentScale = ContentScale.Crop // Crop to fit the bounds
            )

            // 4. ADD A SPACER
            Spacer(modifier = Modifier.width(16.dp))

            // 5. YOUR EXISTING TEXT COLUMN
            Column {
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
                book = Book(
                    bookId = 1,
                    title = "The Lord of the Rings",
                    authorId = 1,
                    isbn = "978-0618640157",
                    genre = "Fantasy",
                    // 6. ADDED A REAL IMAGE URL FOR THE PREVIEW
                    coverUrl = "https://covers.openlibrary.org/b/id/12838421-L.jpg",
                    state = "Available"
                ),
                authorName = "J.R.R. Tolkien"
            ),
            BookWithAuthor(
                book = Book(
                    bookId = 2,
                    title = "Cien Años de Soledad",
                    authorId = 2,
                    isbn = "978-0307350442",
                    genre = "Magic Realism",
                    // 6. ADDED A REAL IMAGE URL FOR THE PREVIEW
                    coverUrl = "https://covers.openlibrary.org/b/id/8478810-L.jpg",
                    state = "Loaned"
                ),
                authorName = "Gabriel García Márquez"
            )
        )
        BookListScreen(books = sampleBooks, navController = rememberNavController())
    }
}