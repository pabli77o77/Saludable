package com.android.saludable

import android.os.Bundle
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.saludable.databinding.FragmentRegistroBinding
import com.android.saludable.databinding.FragmentUserBinding
import kotlinx.android.synthetic.main.dropdown_item.*
import kotlinx.android.synthetic.main.fragment_registro.*


class RegistroFragment : Fragment() {

    private var _binding : FragmentRegistroBinding? = null
    private val binding get() = _binding!!

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
}