package com.example.proyectofinal

import Reserva.Reserva
import Reserva.ReservaService
import Sesion.Sesion
import Sesion.SesionDAO
import Sesion.SesionService
import Usuario.Usuario
import Usuario.UsuarioService
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var editNombre : EditText
    private lateinit var editEmail : EditText
    private lateinit var editHora : EditText
    private lateinit var btnReservar : Button
    private lateinit var sesion : Sesion
    val serviceSesion = SesionService()
    private lateinit var usuario : Usuario
    val serviceReserva = ReservaService()
    private lateinit var layout: RelativeLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info, container, false)
        layout = view.findViewById<RelativeLayout>(R.id.layout)

        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(ContextCompat.getColor(requireContext(), R.color.white))
        shape.cornerRadius = 20f // ajusta este valor para hacer los bordes más o menos redondos


        layout.background = shape
        editHora = view.findViewById<EditText>(R.id.hora)
        editNombre = view.findViewById<EditText>(R.id.nombre)
        editEmail = view.findViewById<EditText>(R.id.email)
        arguments?.let {
            sesion = requireArguments().getSerializable("sesion") as Sesion
            usuario = requireArguments().getSerializable("usuario") as Usuario
            editEmail.setText(usuario.email)
            editNombre.setText(usuario.nombre
            )
            editHora.setText(sesion.hora)
        }


        btnReservar = view.findViewById<Button>(R.id.btn)

        btnReservar.setOnClickListener(this)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onClick(v: View?) {
        updateReserva(sesion)
        editNombre.isVisible = false
        editEmail.isVisible = false
        editHora.isVisible = false
        btnReservar.isVisible = false
        layout.isVisible = false
        val reserva = Reserva(sesion.hora, usuario.email)
        serviceReserva.createReserva(reserva)
        onResume()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = ReservadoFragment()

        val bundle = Bundle()
        bundle.putSerializable("usuario", usuario)

        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameSesiones, fragment)
        fragmentTransaction.commit()
    }

    fun updateReserva(s:Sesion) {
        serviceSesion.updateReserva(s)
    }

    override fun onResume() {
        super.onResume()
        val fragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = SesionesFragment()

        val bundle = Bundle()
        bundle.putSerializable("usuario", usuario)

        fragment.arguments = bundle
        fragmentTransaction.replace(R.id.frameSesiones, fragment)
        fragmentTransaction.commit()
    }
}