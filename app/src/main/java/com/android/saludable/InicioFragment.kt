package com.android.saludable

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.saludable.data.RegistroModel
import com.android.saludable.databinding.FragmentInicioBinding
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.fragment_inicio.*
import java.util.*
import kotlin.collections.ArrayList

private const val BASE_URL = "https://saludapi-default-rtdb.firebaseio.com/"

class InicioFragment : Fragment() {

    private var _binding : FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference
    private lateinit var userId : String
    private var LOCAL_DB : String = "LocalDb"
    private lateinit var arrayAdapter : ArrayAdapter<*>

    override fun onResume() {
        super.onResume()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        obtenerIdUsuario()
        obtenerRegistrosAlimentarios()


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        obtenerRegistrosAlimentarios()

    }


    private fun obtenerRegistrosAlimentarios() {
        db = FirebaseDatabase.getInstance().getReference("alimentacion").child("$userId")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var list = ArrayList<RegistroModel>()
                if(snapshot.hasChildren()){
                    list.clear()
                    if(snapshot.children.iterator().hasNext()){
                        for(data : DataSnapshot in snapshot.children){
                            val model = data.getValue(RegistroModel::class.java)
                            if (model != null) {
                                list.add(model)
                            }
                        }
                    }
                    Collections.sort(list)
                    //var lvRegistrosAlimentarios = binding.lvRegistrosAlimentarios
                    arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
                    //lvRegistrosAlimentarios.adapter = arrayAdapter

                    rv_RegistrosAlimentarios.layoutManager = LinearLayoutManager(context)
                    rv_RegistrosAlimentarios.adapter = RegistroAdapter(list)

                }else{
                    Toast.makeText(context, "La consulta se ejecutó pero no trajo datos", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Error al cargar datos: ${error.message}", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun obtenerIdUsuario() {
        val pref = context?.getSharedPreferences(LOCAL_DB, 0)
        userId = pref?.getString("user_id", "").toString()
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




















