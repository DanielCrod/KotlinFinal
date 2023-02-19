package Reserva

class Reserva {
    val hora : String
    val email : String

    constructor() {
        hora = ""
        email = ""
    }

    constructor(hora : String, email : String) {
        this.hora = hora
        this.email = email
    }
}