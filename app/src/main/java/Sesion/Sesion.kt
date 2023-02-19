package Sesion

import java.time.LocalDateTime

class Sesion : java.io.Serializable {
    val hora : String
    val reservado : Boolean

    constructor() {
        hora = ""
        reservado = false
    }



    constructor(hora : String, reservado: Boolean) {
        this.hora = hora
        this.reservado = reservado
    }

    override fun toString(): String {
        return "$hora"
    }
}