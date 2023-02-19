package Usuario

import android.telecom.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioDAO {

    // Método para crear un usuario
    @POST("usuarios")
    fun createUsuario(@Body usuario: Usuario): retrofit2.Call<Usuario>

    // Método para obtener todos los usuarios
    @GET("usuarios")
    fun getUsuarios(): retrofit2.Call<List<Usuario>>

    // Método para obtener un usuario por su email
    @GET("usuario/{email}/{pwd}")
    fun getUsuario(@Path("email") email: String, @Path("pwd") pwd:String): retrofit2.Call<Usuario>

}