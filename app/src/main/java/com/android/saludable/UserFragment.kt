package com.android.saludable

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.saludable.data.PacienteDataSource
import com.android.saludable.databinding.FragmentUserBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.fragment_user.*
import java.lang.Exception


class UserFragment : Fragment() {

    private var _binding : FragmentUserBinding? = null
    private val binding get() = _binding!!
    lateinit var sexo : String
    private lateinit var db : DatabaseReference
    private lateinit var paciente: PacienteDataSource

    override fun onResume() {
        super.onResume()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setRadioGroupActions()

        etFechaNacimiento.setOnClickListener {
            showDatePickerDialog()
        }

        getTipoTratamiento()

        btnGuardar.setOnClickListener {
            paciente.id = db.push().key
            val nombre = binding.etNombre.text.toString()
            val apellido = binding.etApellido.text.toString()
            val dni = binding.etDni.text.toString()
            val sexo = sexo
            val localidad = binding.etLocalidad.text.toString()
            val fechaNacimiento = binding.etFechaNacimiento.text.toString()
            val tipoTratamiento = binding.actvTipoTratamiento.text.toString()
            val usuario = paciente.id!!//"HAKWDAUIK9bhV7gRzLWPddFGUux1"

            db = FirebaseDatabase.getInstance().getReference("paciente")
            val paciente = PacienteDataSource(nombre, apellido, dni, sexo, fechaNacimiento, localidad, tipoTratamiento, usuario)
            db.child(usuario).setValue(paciente).addOnSuccessListener {

                Toast.makeText(context, "Datos guardados OK", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(context, "Los datos no se guardaron", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun storeUserData(paciente: PacienteDataSource) {
        paciente.id = db.push().key
        val nombre = binding.etNombre.text.toString()
        val apellido = binding.etApellido.text.toString()
        val dni = binding.etDni.text.toString()
        val sexo = sexo
        val localidad = binding.etLocalidad.text.toString()
        val fechaNacimiento = binding.etFechaNacimiento.text.toString()
        val tipoTratamiento = binding.actvTipoTratamiento.text.toString()
        val usuario = paciente.id!!//"HAKWDAUIK9bhV7gRzLWPddFGUux1"

        db = FirebaseDatabase.getInstance().getReference("paciente")
        val paciente = PacienteDataSource(nombre, apellido, dni, sexo, fechaNacimiento, localidad, tipoTratamiento, usuario)
        db.child(usuario).setValue(paciente).addOnSuccessListener {

            Toast.makeText(context, "Datos guardados OK", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(context, "Los datos no se guardaron", Toast.LENGTH_SHORT).show()
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
                // Acá debo guardar el valor seleccionado en la base
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


}