package com.example.proyectofinal


import Usuario.Usuario
import Usuario.UsuarioService
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var switch : Switch
    private lateinit var layout : RelativeLayout
    private lateinit var title : TextView
    private lateinit var forgot : TextView
    private lateinit var account : TextView
    private lateinit var email : EditText
    private lateinit var btn : Button
    private lateinit var username : EditText
    private lateinit var password : EditText
    val serviceUsuario = UsuarioService()
    private lateinit var list : ArrayList<Usuario>
    private lateinit var registerTxt : TextView
    private lateinit var countDownTimer : CountDownTimer


    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        registerTxt = findViewById(R.id.registertxt)
        btn = findViewById(R.id.btn)

        layout = findViewById(R.id.layout)
        switch = findViewById(R.id.switch1)
        forgot = findViewById(R.id.forgot)
        account = findViewById(R.id.registertxt)
        email = findViewById(R.id.email)
        username = findViewById(R.id.user)
        password = findViewById(R.id.password)
        list = ArrayList<Usuario>()
        title = findViewById(R.id.title)


        val tiempoEnMilisegundos = 950L // tiempo en milisegundos para cambiar el color
        val intervaloEnMilisegundos = 500L

        countDownTimer = object : CountDownTimer(tiempoEnMilisegundos, intervaloEnMilisegundos) {
            override fun onTick(millisUntilFinished: Long) {
                // cambiar el color del TextView durante el tiempo restante
                title.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.rojo))
            }

            override fun onFinish() {
                // restaurar el color original del TextView cuando el tiempo haya terminado
                title.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.amarillo))
            }
        }

        email.setOnClickListener(this)
        username.setOnClickListener(this)
        password.setOnClickListener(this)

        registerTxt.setOnClickListener(this)
        btn.setOnClickListener(this)
        registerTxt.paintFlags = registerTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        switch.setOnCheckedChangeListener {
                buttonView, isChecked ->
            if (isChecked) {
                val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                title.startAnimation(fadeInAnimation)
                title.text = "Register"
                title.setText("Register")

                btn.setText("Register")
                forgot.setText("")
                account.setText("")
                email.isVisible = true
            } else {
                val fadeInAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
                title.startAnimation(fadeInAnimation)
                title.text = "Login"
                btn.setText("Login")
                forgot.setText("Forgot your password?")
                account.setText("Don't have an account yet?")
                email.isVisible = false
            }
        }

        //Llamamos al método para rellenar el arraylist
        getLibros()


    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.registertxt -> {
                switch.isChecked = true
            }
        }
        val user = username.text.toString().trim()
        val pass = password.text.toString().trim()
        if(user.isEmpty()) {
            username.error = "Username required"
        } else if (pass.isEmpty()){
            password.error = "Password required"
        } else {
            if (btn.text == "Login") {
                try {
                    if (list.any { it.nombre == username.text.toString() && it.contraseña == password.text.toString() }) {
                        //Condición si ese usuario está en el arrayList
                        Toast.makeText(this, "Usuario encontrado", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Sesiones::class.java)
                        val usu = getUsuario(list)
                        intent.putExtra("usuario", usu)
                        startActivity(intent)
                        overridePendingTransition(R.anim.fadeon, R.anim.fadeout)
                    } else {
                        //Condición si ese usuario no está en el arrayList
                        shakeEditText(title)
                        Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                    }
                } catch (E : java.lang.Exception) {
                    Toast.makeText(this, "Error al iniciar sesión", Toast.LENGTH_LONG).show()
                }
            } else if (password.text.toString().length < 6){
                    password.error = "Mínimo 6 caracteres"
            } else {
                try {
                    val usuario = Usuario(email.text.toString(),
                        username.text.toString(),
                        password.text.toString(),
                        false)
                    list.add(usuario)
                    serviceUsuario.createUsuario(usuario)
                    Toast.makeText(this, "Registrado!", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, Sesiones::class.java)
                    intent.putExtra("usuario", usuario)
                    startActivity(intent )
                    email.setText("")
                    username.setText("")
                    password.setText("")
                } catch (e: java.lang.Exception) {
                    Toast.makeText(this, "Error de registro", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun getLibros() {
        serviceUsuario.getUsuarios().enqueue(object: Callback<List<Usuario>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {
                if (response.isSuccessful)
                {
                    for (usuario in response.body()!!)
                        list.add(usuario)
                        Log.d("a", list.toString())
                } else
                {
                    Log.d("TAG", "Error")
                }
            }

            override fun onFailure(call: Call<List<Usuario>> , t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    fun getUsuario(arrayList: ArrayList<Usuario>): Usuario? {
        for (usuario in arrayList) {
            if (usuario.nombre == username.text.toString() && usuario.contraseña == password.text.toString()) {
                return usuario
            }
        }
        return null
    }

    fun shakeEditText(textView: TextView) {
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        textView.startAnimation(shake)
        countDownTimer.start()
    }

}


