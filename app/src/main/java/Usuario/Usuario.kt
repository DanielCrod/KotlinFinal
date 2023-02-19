package Usuario

class Usuario : java.io.Serializable{
    val email : String
    val nombre : String
    val contraseña : String
    val administrador : Boolean

    constructor() {
        email = ""
        nombre = ""
        contraseña = ""
        administrador = false
    }

    constructor(email : String, nombre : String,  contraseña : String, administrador : Boolean) {
        this.email = email
        this.nombre = nombre
        this.contraseña = contraseña
        this.administrador = administrador
    }
}