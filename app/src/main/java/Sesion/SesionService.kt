package Sesion

import Usuario.Usuario
import Usuario.UsuarioDAO
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SesionService {
    //Instancia de Retrofit
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.119:8080/ServidorREST/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //Método para ver todos las sesiones
    fun getSesiones() : Call<List<Sesion>> {
        return getRetrofit().create(SesionDAO::class.java).getSesiones()
    }


    fun updateReserva(s: Sesion) {
        getRetrofit().create(SesionDAO::class.java).updateReserva(s.hora, s)
            .enqueue(object : Callback<Sesion> {
                override fun onResponse(call: Call<Sesion>, response: Response<Sesion>) {
                    Log.d("TAG", "CALL: $call")
                    Log.d("TAG", "RESPONSE: $response")
                }

                override fun onFailure(call: Call<Sesion>, t: Throwable) {
                    Log.d("TAG", "Error")
                }

            })
    }


}