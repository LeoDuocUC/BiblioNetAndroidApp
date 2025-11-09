package com.example // <-- LÍNEA AÑADIDA

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.ResultSet // <-- Importante para leer la base de datos

fun Application.configureRouting() {
    routing {
        // Este es nuestro primer endpoint
        get("/api/libros") {
            val listaDeLibros = mutableListOf<Libro>()
            
            try {
                // 1. Conectarse a la DB usando nuestro objeto
                val conn = DatabaseConnection.connect()
                val statement = conn.createStatement()
                
                // 2. Ejecutar la consulta SQL (basada en tu DataBase.txt)
                val resultSet = statement.executeQuery("SELECT * FROM Libros") 
                
                // 3. Leer los resultados y convertirlos a objetos 'Libro'
                while (resultSet.next()) {
                    val libro = Libro(
                        LibroID = resultSet.getInt("LibroID"),
                        Titulo = resultSet.getString("Titulo"),
                        AutorID = resultSet.getInt("AutorID"),
                        ISBN = resultSet.getString("ISBN"),
                        Genero = resultSet.getString("Genero"),
                        URLPortada = resultSet.getString("URLPortada"),
                        Estado = resultSet.getString("Estado")
                    )
                    listaDeLibros.add(libro)
                }
                
                // 4. Cerrar la conexión
                resultSet.close()
                statement.close()
                conn.close()

                // 5. Enviar la lista de libros como respuesta JSON
                call.respond(listaDeLibros)

            } catch (e: Exception) {
                // Si algo falla (la contraseña, la consulta, etc.)
                // enviará un mensaje de error
                call.respond(mapOf("error" to e.message))
            }
        }
    }
}