package com.example.proyectofinal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotasAdapter : RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    private val notas = ArrayList<AppNota>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_notas, parent, false)
        return NotaViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val nota = notas[position]
        holder.bind(nota)
    }

    override fun getItemCount(): Int {
        return notas.size
    }

    fun setNotas(notas: List<AppNota>) {
        this.notas.clear()
        this.notas.addAll(notas)
        notifyDataSetChanged()
    }

    class NotaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(nota: AppNota) {
            val txtNota: TextView = itemView.findViewById(R.id.txtNota)
            val txtLaboratorio: TextView = itemView.findViewById(R.id.txtLaboratorio)
            val txtAlumno: TextView = itemView.findViewById(R.id.txtAlumno)

            txtNota.text = "Nota: ${nota.nota}"
            txtLaboratorio.text = "Laboratorio: ${nota.laboratorio}" // Utilizar laboratorioNombre en lugar de laboratorioId
            txtAlumno.text = "Alumno: ${nota.alumno}"
        }
    }
}
