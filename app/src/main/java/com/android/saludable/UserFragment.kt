package com.android.saludable

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.widget.SearchView
import com.android.saludable.api.IPaciente
import com.android.saludable.data.PacienteModel
import com.android.saludable.databinding.FragmentUserBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_user.*
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

const val BASE_URL = "https://saludapi-default-rtdb.firebaseio.com/"

class UserFragment : Fragment() {

    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!
    lateinit var sexo : String
    private lateinit var db : DatabaseReference
    private lateinit var userId : String



    override fun onResume() {
        super.onResume()


        getDataUsuario()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRadioGroupActions()

        etFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        getTipoTratamiento()

        btnGuardar.setOnClickListener {
            val nombre = binding.etNombre.text.toString()
            val apellido = binding.etApellido.text.toString()
            val dni = binding.etDni.text.toString()
            val sexo = sexo
            val localidad = binding.etLocalidad.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()
            val tipoTratamiento = binding.actvTipoTratamiento.text.toString()
            val usuario = userId//"HAKWDAUIK9bhV7gRzLWPddFGUux1"

            db = FirebaseDatabase.getInstance().getReference("paciente")
            val paciente = PacienteModel(nombre, apellido, dni, sexo, fechaNacimiento, localidad, tipoTratamiento, usuario)
            db.child(usuario).setValue(paciente).addOnSuccessListener {

                Toast.makeText(context, "Datos guardados OK", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, "Los datos no se guardaron", Toast.LENGTH_SHORT).show()
            }
        }

        userId = arguments?.getString("userId").toString()
        if(userId.isNotEmpty()) {
            getDataUsuario()
        }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {
                day, month, year -> onDateSelected(day, month, year)
        }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    fun onDateSelected(day:Int, month:Int, year:Int) {
        etFechaNacimiento.setText("$day/$month/$year")
    }

    private fun setRadioGroupActions() {
        binding.rgSexo.setOnCheckedChangeListener {
                rgSexo, i ->
            when(i){
                R.id.rbFemenino -> sexo = rbFemenino.text.toString()
                R.id.rbMasculino -> sexo = rbMasculino.text.toString()
                R.id.rbOtroSexo -> sexo = rbOtroSexo.text.toString()
            }
        }
    }

    private fun getTipoTratamiento() {
        val tratamientos = resources.getStringArray(R.array.tratamientos)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, tratamientos)
        binding.actvTipoTratamiento.setAdapter(arrayAdapter)
    }


    private fun getDataUsuario() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(IPaciente::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            val response = api.getPaciente4("$userId/.json").awaitResponse()
            if(response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    if(response.body() != null) {
                        val data = response.body()!!
                        try {
                            etNombre.setText(data?.nombre)
                            etApellido.setText(data?.apellido)
                            etDni.setText(data?.dni)
                            sexo = data?.sexo.toString()

                            when(sexo) {
                                "Masculino" -> rgSexo.check(rbMasculino.id)
                                "Femenino" -> rgSexo.check(rbFemenino.id)
                                "Otro" -> rgSexo.check(rbOtroSexo.id)
                            }

                            etFechaNacimiento.setText(data?.fechaNacimiento)
                            actvTipoTratamiento.setText(data?.tipoTratamiento, false)
                            etLocalidad.setText(data?.localidad)
                        }catch (e : Exception) {
                            Toast.makeText(context, "error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }else {
                        Toast.makeText(context, "No hay datos para cargar", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

}