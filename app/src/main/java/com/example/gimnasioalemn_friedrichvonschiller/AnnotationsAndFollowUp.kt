package com.example.gimnasioalemn_friedrichvonschiller

import android.R
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.database.StudentHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityAnnotationsAndFollowUpBinding
import java.text.SimpleDateFormat
import com.example.gimnasioalemn_friedrichvonschiller.model.Annotation
import java.util.Date
import java.util.Locale

class AnnotationsAndFollowUp : AppCompatActivity() {

    private lateinit var binding: ActivityAnnotationsAndFollowUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnnotationsAndFollowUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initComponents()
    }

    private fun initUI() {
        val grados = arrayOf("Parvulos", "Prekinder", "Kinder", "Kl1", "Kl2", "Kl3", "Kl4", "Kl5", "Kl6", "Kl7", "Kl8", "Kl9", "Kl10", "Kl11", "Kl12")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, grados)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnergrado.adapter = adapter

        val tipos = arrayOf("Anotacion", "Seguimiento", "Felitacion")
        val adapter2 = ArrayAdapter(this, R.layout.simple_spinner_item, tipos)
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapter2
    }

    private fun initComponents() {
        binding.spinnergrado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val gradoSeleccionado = parent.getItemAtPosition(position).toString()
                StudentHelper().cargarEstudiantesPorGrado(gradoSeleccionado) { estudiantes ->
                    val adapter = ArrayAdapter(this@AnnotationsAndFollowUp, R.layout.simple_spinner_item, estudiantes)
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                    binding.spinnerEstudiante.adapter = adapter
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.btnEnviar.setOnClickListener {
            validParams()
        }
    }

    private fun validParams() {
        val grado = binding.spinnergrado.selectedItem.toString()
        val tipo = binding.spinnerTipo.selectedItem.toString()
        val estudiante = binding.spinnerEstudiante.selectedItem.toString()
        val descripcion = binding.etDescripcion.text.toString()

        if (grado.isEmpty() || tipo.isEmpty() || estudiante.isEmpty() || descripcion.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val teacher = sharedPreferences.getString("USER_NAME", null).toString()
        val fechaActual = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

        guardarAnotacion(Annotation(tipo, descripcion, teacher, fechaActual), estudiante)
    }

    private fun guardarAnotacion(annotation: Annotation, student: String) {
        val studentHelper = StudentHelper()
        studentHelper.guardarAnotacion(annotation, student) { success ->
            if (success) {
                Toast.makeText(this, "Anotación guardada exitosamente", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar la anotación", Toast.LENGTH_SHORT).show()
            }
        }
    }
}