package Sesion

import Usuario.Usuario
import retrofit2.Call
import retrofit2.http.*


interface SesionDAO {
    // Método para obtener todos las sesiones
    @GET("sesiones")
    fun getSesiones(): retrofit2.Call<List<Sesion>>

    @PUT("sesion/{hora}")
    fun updateReserva(@Path("hora") hora:String,@Body s: Sesion) : Call<Sesion>


}