package com.android.saludable

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.saludable.data.RegistroModel
import com.android.saludable.databinding.CardLayoutBinding
import com.android.saludable.databinding.FragmentRegistroBinding
import kotlinx.android.synthetic.main.card_layout.view.*

class RegistroViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = CardLayoutBinding.bind(view)

    fun bind(registro:RegistroModel) {
        binding.cardView.tv_tituloRegistroAlimentario.text = registro.fechaRegistro
        binding.cardView.tv_tipoComida.text = registro.tipoComida
        binding.cardView.tv_comidaPrincipal.text = registro.comidaPrincipal
        binding.cardView.tv_comidaSecundaria.text = registro.comidaSecundaria
        binding.cardView.tv_bebida.text = registro.bebida
        if(registro.ingirioPostre)
            binding.cardView.tv_detallePostre.text = "Sí: ${registro.postre}"
        if(registro.tentacionOtroAlimento)
            binding.cardView.tv_alimentoTentacion.text = "Sí: ${registro.otroAlimento}"
        buildString{
            append(binding.cardView.tv_preguntaHambre.text)
            append(": ")
            append(registro.hambre)
        }
    }
}