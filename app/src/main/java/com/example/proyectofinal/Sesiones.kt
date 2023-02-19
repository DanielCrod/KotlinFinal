package com.example.proyectofinal

import Usuario.Usuario
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class Sesiones : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_sesiones)

            val usuario = intent.getSerializableExtra("usuario") as Usuario


            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            var fragment = SesionesFragment()

            val bundle = Bundle()
            bundle.putSerializable("usuario", usuario)

            fragment.arguments = bundle
            fragmentTransaction.replace(R.id.frameSesiones, fragment)
            fragmentTransaction.commit()

    }
}