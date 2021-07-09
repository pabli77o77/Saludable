package com.android.saludable

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.saludable.data.RegistroModel
import kotlinx.android.synthetic.main.card_layout.view.*

class RegistroAdapter(var list : ArrayList<RegistroModel>) : RecyclerView.Adapter<RegistroViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegistroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return RegistroViewHolder(view)
    }

    override fun onBindViewHolder(holder: RegistroViewHolder, position: Int) {
        holder.itemView.tv_tituloRegistroAlimentario.text = list[position].tipoComida
        holder.itemView.tv_tipoComida.text = list[position].fechaRegistro
    }

    override fun getItemCount(): Int {
        return list.size
    }

}