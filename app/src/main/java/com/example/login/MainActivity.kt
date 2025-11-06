package com.example.login

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media // Importación para el audio
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.login.data.AppDatabase
import com.example.login.data.Author
import com.example.login.data.Book
import com.example.login.ui.bookdetail.BookDetailScreen
import com.example.login.ui.booklist.BookListScreen
import com.example.login.ui.home.HomeScreen
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

    // --- LÓGICA DE CÁMARA EXISTENTE ---
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                launchCameraIntent()
            } else {
                Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show()
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Foto tomada con éxito", Toast.LENGTH_SHORT).show()
            }
        }

    fun launchCameraIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePictureLauncher.launch(takePictureIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se puede acceder a la cámara", Toast.LENGTH_SHORT).show()
        }
    }

    fun checkCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_GRANTED) {
            launchCameraIntent()
        } else {
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    // --- FIN LÓGICA DE CÁMARA ---


    // --- NUEVA LÓGICA DE MICRÓFONO ---
    private val requestMicrophonePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permiso concedido, lanzamos el grabador de audio
                launchAudioRecorderIntent()
            } else {
                // Permiso denegado
                Toast.makeText(this, "Permiso de micrófono denegado", Toast.LENGTH_SHORT).show()
            }
        }

    private val recordAudioLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                Toast.makeText(this, "Grabación de audio terminada con éxito", Toast.LENGTH_SHORT).show()
            }
        }

    fun launchAudioRecorderIntent() {
        val takeAudioIntent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
        try {
            recordAudioLauncher.launch(takeAudioIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se puede acceder al grabador de audio", Toast.LENGTH_SHORT).show()
        }
    }

    // Función principal que comprueba permisos y lanza el micrófono
    fun checkMicrophonePermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)
            == PackageManager.PERMISSION_GRANTED) {
            // Ya tiene el permiso
            launchAudioRecorderIntent()
        } else {
            // Pedimos el permiso
            requestMicrophonePermissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
        }
    }
    // --- FIN LÓGICA DE MICRÓFONO ---


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CoroutineScope(Dispatchers.IO).launch {
            if (database.authorDao().getAuthorCount() == 0) {
                database.authorDao().insertAuthor(Author(authorId = 1, fullName = "J.R.R. Tolkien"))
                database.authorDao().insertAuthor(Author(authorId = 2, fullName = "Gabriel García Márquez"))
                
                // --- BOOK DATA WITH IMAGE URLs (CORRECTED) ---
                database.bookDao().insertBook(
                    Book(
                        bookId = 1,
                        title = "The Lord of the Rings",
                        authorId = 1,
                        isbn = "978-0618640157",
                        genre = "Fantasy",
                        coverUrl = "https://covers.openlibrary.org/b/id/12838421-L.jpg",
                        state = "Available"
                    )
                )
                database.bookDao().insertBook(
                    Book(
                        bookId = 2,
                        title = "Cien Años de Soledad",
                        authorId = 2,
                        isbn = "978-0307350442",
                        genre = "Magic Realism",
                        coverUrl = "https://covers.openlibrary.org/b/id/8478810-L.jpg", // <-- Corrected URL
                        state = "Loaned"
                    )
                )
                // ---------------------------------
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
                    myReservationsViewModel = myReservationsViewModel,
                    onScanClick = { checkCameraPermissionAndLaunch() },
                    onMicrophoneClick = { checkMicrophonePermissionAndLaunch() }
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
    myReservationsViewModel: MyReservationsViewModel,
    onScanClick: () -> Unit,
    onMicrophoneClick: () -> Unit
) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginForm(navController = navController, viewModel = loginViewModel)
        }
        composable("home/{nombre}/{apellido}") { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: "User"
            val apellido = backStackEntry.arguments?.getString("apellido") ?: ""
            HomeScreen(
                nombre = nombre,
                apellido = apellido,
                navController = navController,
                onScanClick = onScanClick,
                onMicrophoneClick = onMicrophoneClick
            )
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