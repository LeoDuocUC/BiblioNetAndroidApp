ppackage com.example.login // <-- Asegúrate que sea tu paquete

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ❗️ IMPORTANTE: Esta es la IP MÁGICA
    // El emulador de Android no puede ver "localhost" o "127.0.0.1".
    // 10.0.2.2 es la dirección especial que usa el emulador
    // para conectarse al "localhost" de tu computadora.
    private const val BASE_URL = "http://10.0.2.2:8080/"

    /**
     * Creamos la instancia de Retrofit usando 'lazy'.
     * Esto significa que solo se creará la primera vez que
     * la necesitemos, no cada vez que la llamemos.
     */
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Le decimos que use Gson para "traducir" JSON
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Creamos una implementación de nuestra interfaz ApiService
        retrofit.create(ApiService::class.java)
    }
}