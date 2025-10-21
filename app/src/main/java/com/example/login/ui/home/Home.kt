package com.example.login.ui.home

import androidx.compose.foundation.background // <-- IMPORTANTE
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape // <-- IMPORTANTE
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip // <-- IMPORTANTE
import androidx.compose.ui.graphics.Color // <-- IMPORTANTE
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HomeScreen(
    nombre: String,
    apellido: String,
    navController: NavController,
    onScanClick: () -> Unit,
    onMicrophoneClick: () -> Unit
) {
    // Contenedor exterior para ocupar toda la pantalla
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0)), // Fondo gris claro para la pantalla
        contentAlignment = Alignment.Center // Centra la tarjeta
    ) {
        // Columna principal con efectos de tarjeta
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Ancho de la tarjeta
                .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas
                .background(Color.White) // Fondo blanco
                .padding(24.dp), // Relleno interno
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Se mantiene el contenido, con ligeros ajustes de tamaño y espacio
            Text("BiblioNet \uD83D\uDCDA $nombre $apellido", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(24.dp))

            // Botón "Escanear"
            Button(
                onClick = onScanClick,
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Escanear")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Buscar por voz"
            Button(
                onClick = onMicrophoneClick,
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Buscar por voz")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Ver Catálogo de Libros"
            Button(
                onClick = { navController.navigate("booklist") },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Ver Catálogo de Libros")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Mis Préstamos"
            Button(
                onClick = { navController.navigate("myloans") },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Mis Préstamos")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Mis Reservas"
            Button(
                onClick = { navController.navigate("myreservations") },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Mis Reservas")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón "Cerrar Sesión"
            Button(
                onClick = {
                    navController.navigate("login") {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.85f)
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}