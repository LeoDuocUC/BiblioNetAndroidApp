package com.example.login.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(nombre: String, apellido: String, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Bienvenido(a) $nombre $apellido", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { navController.navigate("booklist") }) {
            Text("Ver Catálogo de Libros")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("myloans") }) {
            Text("Mis Préstamos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        // --- THIS BUTTON WAS MISSING ---
        Button(onClick = { navController.navigate("myreservations") }) {
            Text("Mis Reservas")
        }
        // -----------------------------
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate("login") {
                popUpTo(navController.graph.startDestinationId) { inclusive = true }
            }
        }) {
            Text("Cerrar Sesión")
        }
    }
}