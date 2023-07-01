package com.example.proyectofinal

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class OpcionesDialogFragment : DialogFragment() {
    private var alumnoId: Int = -1
    private var cursoId: Int = -1

    companion object {
        fun newInstance(alumnoId: Int, cursoId: Int): OpcionesDialogFragment {
            val fragment = OpcionesDialogFragment()
            fragment.alumnoId = alumnoId
            fragment.cursoId = cursoId
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Opciones")
                .setItems(arrayOf("Asistencias", "Notas")) { dialog, which ->
                    when (which) {
                        0 -> {
                            // Redirigir a la actividad de Asistencias (DetallesCursoActivity)
                            val intent = Intent(requireContext(), DetallesCursoActivity::class.java)
                            intent.putExtra("alumnoId", alumnoId)
                            intent.putExtra("cursoId", cursoId)
                            requireContext().startActivity(intent)
                            dismiss()
                        }
                        1 -> {
                            // Redirigir a la actividad de Notas
                            val intent = Intent(requireContext(), NotasActivity::class.java)
                            intent.putExtra("alumnoId", alumnoId)
                            intent.putExtra("cursoId", cursoId)
                            requireContext().startActivity(intent)
                            dismiss()
                        }
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
