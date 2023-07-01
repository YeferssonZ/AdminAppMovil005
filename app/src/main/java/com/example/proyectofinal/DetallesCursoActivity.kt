package com.example.proyectofinal

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_detalles_curso.*

class DetallesCursoActivity : AppCompatActivity() {
    private lateinit var asistenciasAdapter: AsistenciasAdapter
    private val asistenciasList = ArrayList<AppAsistencia>()
    private var alumnoId: Int = -1
    private var alumnoIdProcessed = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_curso)

        // Obtener el ID del curso seleccionado desde el intent
        val cursoId = intent.getIntExtra("cursoId", -1)

        // Inicializar el RecyclerView y el adaptador de asistencias
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAsistencias)
        asistenciasAdapter = AsistenciasAdapter()
        recyclerView.adapter = asistenciasAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (!alumnoIdProcessed) {
            alumnoId = intent.getIntExtra("alumnoId", -1)
            alumnoIdProcessed = true
        }

        obtenerAsistenciasAlumno(cursoId, alumnoId)

        btnSalirCourses.setOnClickListener {
            onBackPressed()
        }
    }

    private fun obtenerAsistenciasAlumno(cursoId: Int, alumnoId: Int) {
        Log.d("DetallesCursoActivity", "ID del alumno: $alumnoId")

        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/asistencia/?alumno=$alumnoId&curso=$cursoId"
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                // Procesar la respuesta JSON y actualizar el adaptador de asistencias
                for (i in 0 until response.length()) {
                    val asistencia = response.getJSONObject(i)
                    val asistenciaId = asistencia.getInt("id")
                    val fecha = asistencia.getString("fecha")
                    val estado = asistencia.getString("estado")
                    val alumno = asistencia.getInt("alumno")
                    val curso = asistencia.getInt("curso")

                    // Modificar el estado de asistencia
                    val estadoModificado = when (estado) {
                        "A" -> "Asistió"
                        "T" -> "Tardanza"
                        "F" -> "Faltante"
                        else -> estado  // Mantener el estado original si no coincide con los valores conocidos
                    }

                    val appAsistencia = AppAsistencia(asistenciaId, fecha, estadoModificado, alumno, curso)
                    asistenciasList.add(appAsistencia)
                }

                asistenciasAdapter.setAsistencias(asistenciasList)
            },
            { error ->
                // Manejar el error de la solicitud
                Toast.makeText(this, "Error al obtener las asistencias del alumno", Toast.LENGTH_SHORT).show()
            })

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(request)
    }
}
