package Reserva

import Usuario.Usuario
import Usuario.UsuarioDAO
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReservaService {

    // Instancia de Retrofit
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.119:8080/ServidorREST/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Método para crear un usuario
    fun createReserva(reserva: Reserva) {
        getRetrofit().create(ReservaDAO::class.java).createReserva(reserva).enqueue(object :
            Callback<Reserva> {
            override fun onResponse(call: Call<Reserva>, response: Response<Reserva>) {
                Log.d("TAG", "CALL: $call")
                Log.d("TAG", "RESPONSE: $response")
            }
            override fun onFailure(call: Call<Reserva>, t: Throwable) {
                // Procesar error en la petición
                Log.d("TAG", "Error")
            }
        })
    }
}