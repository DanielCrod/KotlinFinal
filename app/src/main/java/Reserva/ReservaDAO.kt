package Reserva

import Usuario.Usuario
import retrofit2.http.Body
import retrofit2.http.POST

interface ReservaDAO {
    @POST("reservas")
    fun createReserva(@Body reserva: Reserva): retrofit2.Call<Reserva>
}