package com.example.login // <-- Asegúrate que sea tu paquete

import retrofit2.http.GET

// NOTA: Debes tener una data class 'Libro' en tu proyecto
// que coincida con la de la API
// data class Libro(val LibroID: Int, val Titulo: String, ...)

interface ApiService {

    /**
     * Esta función llamará al endpoint /api/libros de tu API Ktor
     * y convertirá el JSON en una List<Libro>
     */
    @GET("api/libros")
    suspend fun getAllLibros(): List<Libro>

    // --- PRÓXIMOS PASOS ---
    // Cuando quieras añadir más funciones, las pones aquí.
    // Por ejemplo:
    //
    // @GET("api/autores")
    // suspend fun getAllAutores(): List<Autor>
    //
    // @POST("api/prestamos")
    // suspend fun crearPrestamo(@Body nuevoPrestamo: Prestamo)

}