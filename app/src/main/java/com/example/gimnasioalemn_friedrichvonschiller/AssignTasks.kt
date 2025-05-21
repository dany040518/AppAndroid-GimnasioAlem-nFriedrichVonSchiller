package com.example.gimnasioalemn_friedrichvonschiller

import android.R
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityAssignTasksBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AssignTasks : AppCompatActivity() {

    private lateinit var binding: ActivityAssignTasksBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initComponents()
    }

    private fun initUI() {
        val grados = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        // Crea un ArrayAdapter para llenar el Spinner
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, grados)
        // Especifica el layout para el dropdown del Spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Asocia el adapter al Spinner
        binding.spinnerGrado.adapter = adapter
    }

    private fun initComponents() {
        // Configurar el botón de asignar tareas
        binding.btnAsignarTarea.setOnClickListener {
            validateParams()
        }
    }

    private fun validateParams() {
        val grado = binding.spinnerGrado.selectedItem.toString()
        val tarea = binding.etTitulo.text.toString().trim()
        val fecha = binding.etFechaEntrega.text.toString().trim()
        val asignatura = binding.etAsignatura.text.toString().trim()

        // Validación de que el campo de título no esté vacío
        if (tarea.isEmpty() || grado.isEmpty() || grado == "Seleccionar grado" || fecha.isEmpty() || asignatura.isEmpty()) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }
        if(!validateDate(fecha)) asignarTarea(grado, tarea, fecha)


    }

    private fun validateDate(fecha: String): Boolean{
        // Validación de que la fecha seleccionada no sea antes de la fecha actual
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val fechaActual = Calendar.getInstance().time
        val fechaSeleccionada: Date?

        try {
            fechaSeleccionada = sdf.parse(fecha)
            if (fechaSeleccionada.before(fechaActual)) {
                Toast.makeText(this, "La fecha de entrega no puede ser antes de la fecha actual.", Toast.LENGTH_SHORT).show()
                return false
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Formato de fecha incorrecto.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun asignarTarea(grado: String, tarea: String, fecha: String) {
        // Aquí puedes agregar la lógica para asignar la tarea
        // Por ejemplo, guardar en la base de datos o enviar a un servidor
        Toast.makeText(this, "Tarea asignada a grado $grado: $tarea con fecha de entrega $fecha", Toast.LENGTH_SHORT).show()
    }
}