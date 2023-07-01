package com.example.proyectofinal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load

class AdaptadorCarreras(val ListaElementos:ArrayList<Carreras>): RecyclerView.Adapter<AdaptadorCarreras.ViewHolder>() {

    override fun getItemCount(): Int {
        return ListaElementos.size;
    }
    class ViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val fImagen = itemView.findViewById<ImageView>(R.id.imgCarrera)
        val fNombre = itemView.findViewById<TextView>(R.id.txtCarrera)

        //set the onclick listener for the singlt list item
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.fNombre?.text=ListaElementos[position].nombre
        holder?.fImagen?.load(ListaElementos[position].imagen)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.elementoscarrera, parent, false);
        return ViewHolder(v);
    }
}