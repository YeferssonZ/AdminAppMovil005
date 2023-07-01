package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AsistenciasAdapter : RecyclerView.Adapter<AsistenciasAdapter.AsistenciaViewHolder>() {
    private val asistencias = ArrayList<AppAsistencia>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsistenciaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_asistencias, parent, false)
        return AsistenciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AsistenciaViewHolder, position: Int) {
        val asistencia = asistencias[position]
        holder.bind(asistencia)
    }

    override fun getItemCount(): Int {
        return asistencias.size
    }

    fun setAsistencias(asistencias: List<AppAsistencia>) {
        this.asistencias.clear()
        this.asistencias.addAll(asistencias)
        notifyDataSetChanged()
    }

    class AsistenciaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(asistencia: AppAsistencia) {
            // Configurar los valores de los elementos de vista en el ViewHolder
            val txtAsistenciaId: TextView = itemView.findViewById(R.id.txtAsistenciaId)
            val txtFecha: TextView = itemView.findViewById(R.id.txtFecha)
            val txtEstado: TextView = itemView.findViewById(R.id.txtEstado)

            txtAsistenciaId.text = "ID de la asistencia: ${asistencia.id}"
            txtFecha.text = "Fecha: ${asistencia.fecha}"
            txtEstado.text = "Estado: ${asistencia.estado}"
        }
    }
}
