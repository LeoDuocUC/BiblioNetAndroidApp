package com.example.login

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.data.AppDatabase
import com.example.login.data.Author
import com.example.login.data.Book
import com.example.login.ui.bookdetail.BookDetailScreen
import com.example.login.ui.booklist.BookListScreen
import com.example.login.ui.home.HomeScreen // <-- This import fixes the error
import com.example.login.ui.myloans.MyLoansScreen
import com.example.login.ui.myreservations.MyReservationsScreen
import com.example.login.ui.theme.LoginTheme
import com.example.login.viewModel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(database.userDao())
    }

    private val bookListViewModel: BookListViewModel by viewModels {
        BookListViewModelFactory(database.bookDao(), database.loanDao(), database.reservationDao())
    }

    private val myLoansViewModel: MyLoansViewModel by viewModels {
        MyLoansViewModelFactory(database.loanDao(), database.bookDao())
    }

    private val myReservationsViewModel: MyReservationsViewModel by viewModels {
        MyReservationsViewModelFactory(database.reservationDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        CoroutineScope(Dispatchers.IO).launch {
            if (database.authorDao().getAuthorCount() == 0) {
                database.authorDao().insertAuthor(Author(authorId = 1, fullName = "J.R.R. Tolkien"))
                database.authorDao().insertAuthor(Author(authorId = 2, fullName = "Gabriel García Márquez"))
                database.bookDao().insertBook(Book(1, "The Lord of the Rings", 1, "978-0618640157", "Fantasy", "", "Available"))
                database.bookDao().insertBook(Book(2, "Cien Años de Soledad", 2, "978-0307350442", "Magic Realism", "", "Loaned"))
            }

            if (database.userDao().findUserByEmail("test@test.com") == null) {
                val testUser = com.example.login.data.User(fullName = "Test User", email = "test@test.com", passwordHash = "123456")
                database.userDao().insertUser(testUser)
            }
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LoginTheme {
                AppNavigator(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    bookListViewModel = bookListViewModel,
                    myLoansViewModel = myLoansViewModel,
                    myReservationsViewModel = myReservationsViewModel
                )
            }
        }
    }
}

@Composable
fun AppNavigator(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    bookListViewModel: BookListViewModel,
    myLoansViewModel: MyLoansViewModel,
    myReservationsViewModel: MyReservationsViewModel
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginForm(navController = navController, viewModel = loginViewModel)
        }
        composable("home/{nombre}/{apellido}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "User"
            val apellido = backStackEntry.arguments?.getString("apellido") ?: ""
            HomeScreen(nombre = nombre, apellido = apellido, navController = navController)
        }
        composable("booklist") {
            val books by bookListViewModel.books.collectAsState()
            BookListScreen(books = books, navController = navController)
        }
        composable("bookdetail/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            LaunchedEffect(bookId) {
                if (bookId != null) {
                    bookListViewModel.findBookById(bookId)
                }
            }
            val selectedBook by bookListViewModel.selectedBook.collectAsState()
            BookDetailScreen(book = selectedBook, navController = navController, viewModel = bookListViewModel)
        }
        composable("myloans") {
            val loans by myLoansViewModel.userLoans.collectAsState()
            MyLoansScreen(
                loans = loans,
                navController = navController,
                viewModel = myLoansViewModel
            )
        }
        composable("myreservations") {
            val reservations by myReservationsViewModel.userReservations.collectAsState()
            MyReservationsScreen(
                reservations = reservations,
                navController = navController,
                viewModel = myReservationsViewModel
            )
        }
    }
}

@Composable
fun LoginForm(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState == LoginUiState.SUCCESS) {
            val userFullName = "Test User"
            val (nombre, apellido) = userFullName.split(" ").let { it.getOrElse(0) { "" } to it.getOrElse(1) { "" } }
            navController.navigate("home/$nombre/$apellido") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
            viewModel.resetLoginState()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login(email, password) },
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Login")
        }
        if (loginState == LoginUiState.ERROR) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Usuario o contraseña incorrecta", color = Color.Red, fontSize = 12.sp)
        }
    }
}