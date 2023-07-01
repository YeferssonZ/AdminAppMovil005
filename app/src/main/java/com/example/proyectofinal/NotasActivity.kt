package com.example.proyectofinal

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_listado_cursos.*
import kotlinx.android.synthetic.main.activity_notas.*

class NotasActivity : AppCompatActivity() {

    private lateinit var notasAdapter: NotasAdapter
    private val notasList = ArrayList<AppNota>()
    private var alumnoId: Int = -1
    private var cursoId: Int = -1
    private val laboratoriosMap = HashMap<Int, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notas)

        // Obtener los IDs del alumno y curso seleccionados desde el intent
        alumnoId = intent.getIntExtra("alumnoId", -1)
        cursoId = intent.getIntExtra("cursoId", -1)

        // Inicializar el RecyclerView y el adaptador de notas
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewNotas)
        notasAdapter = NotasAdapter()
        recyclerView.adapter = notasAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        obtenerNotasAlumno(alumnoId)

        obtenerLaboratorios()

        btnSalirCourses2.setOnClickListener {
            onBackPressed()
        }
    }

    private fun obtenerLaboratorios() {
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/laboratorio/"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                for (i in 0 until response.length()) {
                    val laboratorio = response.getJSONObject(i)
                    val laboratorioId = laboratorio.getInt("id")
                    val laboratorioNombre = laboratorio.getString("nombre")

                    laboratoriosMap[laboratorioId] = laboratorioNombre
                }
            },
            { error ->
                // Manejar el error de la solicitud
                Log.e("NotasActivity", "Error al obtener la lista de laboratorios: ${error.message}")
            })

        queue.add(request)
    }

    private fun obtenerNotasAlumno(alumnoId: Int) {
        Log.d("NotasActivity", "ID del alumno seleccionado: $alumnoId")
        Log.d("NotasActivity", "ID del curso seleccionado: $cursoId")
        val queue = Volley.newRequestQueue(this)
        val urlAPI = getString(R.string.urlAPI)
        val url = "$urlAPI/api/admin/nota-by-alumno-curso/$cursoId/?alumno=$alumnoId"

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                val notasTemp = ArrayList<AppNota>()

                // Obtener todas las notas del alumno sin filtrar
                for (i in 0 until response.length()) {
                    val nota = response.getJSONObject(i)
                    val notaId = nota.getInt("id")
                    val notaValor = nota.getString("nota")
                    val laboratorioId = nota.getInt("laboratorio")
                    val notaAlumnoId = nota.getInt("alumno")

                    val laboratorioNombre = laboratoriosMap[laboratorioId] ?: ""
                    val appNota = AppNota(notaId, notaValor, laboratorioId, notaAlumnoId, cursoId)
                    notasTemp.add(appNota)
                }

                // Filtrar las notas por el curso seleccionado
                val notasFiltradas = notasTemp.filter { it.curso == cursoId }

                // Configurar las notas filtradas en el adaptador
                notasAdapter.setNotas(notasFiltradas)
            },
            { error ->
                // Manejar el error de la solicitud
                Toast.makeText(this, "Error al obtener las notas del alumno", Toast.LENGTH_SHORT).show()
                Log.e("NotasActivity", "Error: ${error.message}")
            })

        // Agregar la solicitud a la cola de solicitudes de Volley
        queue.add(request)
    }
}