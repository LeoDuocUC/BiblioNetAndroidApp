package com.example // <-- LÍNEA AÑADIDA

import kotlinx.serialization.Serializable

@Serializable // <-- Le dice a Ktor que esto se puede convertir a JSON
data class Libro(
    val LibroID: Int,
    val Titulo: String,
    val AutorID: Int,
    val ISBN: String?, // '?' significa que puede ser nulo (NULL)
    val Genero: String?,
    val URLPortada: String?,
    val Estado: String
)