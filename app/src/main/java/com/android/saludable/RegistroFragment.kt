package com.android.saludable

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.saludable.api.IRegistro
import com.android.saludable.data.RegistroModel
import com.android.saludable.databinding.FragmentRegistroBinding
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_registro.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

private const val BASE_URL = "https://saludapi-default-rtdb.firebaseio.com/"

class RegistroFragment : Fragment() {

    private var _binding : FragmentRegistroBinding? = null
    private val binding get() = _binding!!
    private lateinit var userId : String
    private var LOCAL_DB : String = "LocalDb"

    override fun onResume() {
        super.onResume()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistroBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTipoComida()
        checkBoxActions()
        tipoComidaActions()
        obtenerIdUsuario()
        binding.btnGuardar.setOnClickListener(){
            insertarRegistroAlimentario()
        }
    }

    private fun getTipoComida() {
        val comidas = resources.getStringArray(R.array.comidas)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, comidas)
        binding.spinnerComidas.adapter = arrayAdapter
    }

    private fun tipoComidaActions() {
        binding.spinnerComidas.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seleccion : Any = spinnerComidas.selectedItem
                if(seleccion == "Almuerzo" || seleccion == "Cena") {
                    tvPreguntaPostre.visibility = View.VISIBLE
                    chk_postreSi.visibility = View.VISIBLE
                    chk_postreNo.visibility = View.VISIBLE
                    til_postre.visibility = View.VISIBLE
                }
                if(seleccion == "Desayuno" || seleccion == "Merienda") {
                    tvPreguntaPostre.visibility = View.GONE
                    chk_postreSi.visibility = View.GONE
                    chk_postreNo.visibility = View.GONE
                    til_postre.visibility = View.GONE
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun checkBoxActions() {
        binding.chkPostreSi.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.chkPostreNo.isChecked = !isChecked
                binding.tilPostre.visibility = View.VISIBLE
            }else {
                binding.chkPostreNo.isChecked = !isChecked
                binding.tilPostre.visibility = View.GONE
            }
        }

        binding.chkPostreNo.setOnCheckedChangeListener { buttonView, isChecked ->
                if(isChecked) {
                    binding.chkPostreSi.isChecked = !isChecked
                    binding.tilPostre.visibility = View.GONE
                }else {
                    binding.chkPostreSi.isChecked = !isChecked
                    binding.tilPostre.visibility = View.VISIBLE
                }
        }

        binding.chkTentacionSi.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.chkTentacionNo.isChecked = !isChecked
                binding.tilTentacion.visibility = View.VISIBLE
            }else {
                binding.chkTentacionNo.isChecked = !isChecked
                binding.tilTentacion.visibility = View.GONE
            }
        }

        binding.chkTentacionNo.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                binding.chkTentacionSi.isChecked = !isChecked
                binding.tilTentacion.visibility = View.GONE
            }else {
                binding.chkTentacionSi.isChecked = !isChecked
                binding.tilTentacion.visibility = View.VISIBLE
            }
        }

        binding.chkHambreSi.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.chkHambreNo.isChecked = !isChecked
            binding.chkHambreSi.isChecked = isChecked
        }

        binding.chkHambreNo.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.chkHambreSi.isChecked = !isChecked
            binding.chkHambreNo.isChecked = isChecked
        }
    }

    private fun obtenerIdUsuario() {
        val pref = context?.getSharedPreferences(LOCAL_DB, 0)
        userId = pref?.getString("user_id", "").toString()
    }

    private fun insertarRegistroAlimentario(){
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val responseHolder = api.create(IRegistro::class.java)
        val registroPost = RegistroModel(
            etBebida.text.toString(),
            etComidaPrincipal.text.toString(),
            etComidaSecundaria.text.toString(),
            obtenerFechaYHoraActual(),
            chk_hambreSi.isChecked,
            chk_postreSi.isChecked,
            etAlimentoTentacion.text.toString(),
            etPostre.text.toString(),
            chk_tentacionSi.isChecked,
            spinnerComidas.selectedItem.toString()
        )

        try {
                val call = responseHolder.insertarRegistroPost("$userId/.json", registroPost)
                call.enqueue(object : Callback<RegistroModel> {
                    override fun onResponse(
                        call: Call<RegistroModel>,
                        response: Response<RegistroModel>
                    ) {
                        alert("Nuevo Registro", "Datos guardados correctamente")
                    }

                    override fun onFailure(call: Call<RegistroModel>, t: Throwable) {
                        Toast.makeText(context, "${t.message}", Toast.LENGTH_SHORT).show()
                    }

                })
        }catch (e : Exception){
            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun obtenerFechaYHoraActual(): String {

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val fechaHora : String = currentDate.toString()
        return fechaHora
    }

    private fun alert(titulo: String, mensaje: String) {
        val view = View.inflate(context, R.layout.dialog_view, null)
        val builder = AlertDialog.Builder(context)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        view.tv_alert_titulo.setText(titulo)
        view.tv_alert_mensaje.setText(mensaje)
        view.btnConfirmAlert.setOnClickListener {
            val fragment = activity?.supportFragmentManager?.beginTransaction()
            fragment?.replace(R.id.fragment_container, InicioFragment())?.commit()
            dialog.dismiss()
        }
    }

}