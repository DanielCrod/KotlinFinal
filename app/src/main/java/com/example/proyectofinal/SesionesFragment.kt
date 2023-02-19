package com.example.proyectofinal

import Sesion.Sesion
import Sesion.SesionService
import Usuario.Usuario
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SesionesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SesionesFragment : Fragment(), AdapterView.OnItemClickListener {
    val sesionService = SesionService()
    private lateinit var arrayList: ArrayList<Sesion>
    private lateinit var usuario : Usuario
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var list : ListView
    private lateinit var adaptador : AdaptadorPersonalizado

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
        val view = inflater.inflate(R.layout.fragment_sesiones, container, false)
        arrayList = ArrayList<Sesion>()
        list = view.findViewById<ListView>(R.id.list)
        adaptador = AdaptadorPersonalizado(requireContext(), arrayList)
        list.adapter = adaptador
        getSesiones()

        usuario = arguments?.getSerializable("usuario") as Usuario


        list.setOnItemClickListener(this)

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SesionesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SesionesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun getSesiones() {
        sesionService.getSesiones().enqueue(object: Callback<List<Sesion>> {
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(call: Call<List<Sesion>>, response: Response<List<Sesion>>) {
                if (response.isSuccessful) {
                    for (sesion in response.body()!!) {
                        arrayList.add(sesion)
                    }
                    adaptador.notifyDataSetChanged()
                } else
                {
                    Log.d("TAG", "Error")
                }
            }

            override fun onFailure(call: Call<List<Sesion>>, t: Throwable) {
                // something went completely south (like no internet connection)
                t.message?.let { Log.d("Error", it) }
            }
        })
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {


            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            var fragment = InfoFragment()
            val args = Bundle()
            val selectedItem = arrayList[position]
            val reservado = arrayList[position].reservado

            if (reservado == false) {
                val anim = ScaleAnimation(1f,
                    0.8f,
                    1f,
                    1f,
                    requireView().width / 2f,
                    requireView().height / 2f)
                anim.duration = 1000
                if (view != null) {
                    view.startAnimation(anim)
                }
                args.putSerializable("sesion", selectedItem)
                args.putSerializable("usuario", usuario)
                fragment.arguments = args
                fragmentTransaction.replace(R.id.frameInfo, fragment)
                fragmentTransaction.commit()

            } else {
                val animation = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                view?.startAnimation(animation)
            }


    }
}