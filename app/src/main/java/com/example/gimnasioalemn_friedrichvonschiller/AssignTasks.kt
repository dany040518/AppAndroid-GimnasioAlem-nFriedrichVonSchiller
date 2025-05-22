package com.example.gimnasioalemn_friedrichvonschiller

import android.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.database.TaskHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityAssignTasksBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Task
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
        val grados = arrayOf("Parvulos", "Prekinder", "Kinder", "Kl1", "Kl2", "Kl3", "Kl4", "Kl5", "Kl6", "Kl7", "Kl8", "Kl9", "Kl10", "Kl11", "Kl12")
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
        binding.etFechaEntrega.setOnClickListener {
            // Obtener fecha actual para que el DatePicker empiece en hoy
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Crear el DatePickerDialog
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                // selectedMonth va de 0 a 11, así que le sumamos 1 para mostrar bien
                val fechaSeleccionada = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
                binding.etFechaEntrega.setText(fechaSeleccionada)
            }, year, month, day)

            // Mostrar el DatePicker
            datePickerDialog.show()
        }
    }

    private fun validateParams() {
        val grado = binding.spinnerGrado.selectedItem.toString()
        val titulo = binding.etTitulo.text.toString().trim()
        val fecha = binding.etFechaEntrega.text.toString().trim()
        val asignatura = binding.etAsignatura.text.toString().trim()

        // Validación de que el campo de título no esté vacío
        if (titulo.isEmpty() || grado.isEmpty() || grado == "Seleccionar grado" || fecha.isEmpty() || asignatura.isEmpty()) {
            Toast.makeText(this, "Llene todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }
        if(validateDate(fecha)) asignarTarea(Task(titulo, fecha, asignatura, grado))
        else Toast.makeText(this, "La fecha de entrega no puede ser antes de la fecha actual.", Toast.LENGTH_SHORT).show()
    }

    private fun validateDate(fecha: String): Boolean {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return try {
            val fechaSeleccionada = Calendar.getInstance().apply {
                time = sdf.parse(fecha) ?: return false
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val hoy = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (fechaSeleccionada.before(hoy)) {
                Toast.makeText(this, "La fecha de entrega no puede ser antes de la fecha actual.", Toast.LENGTH_SHORT).show()
                false
            } else {
                true
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Formato de fecha incorrecto.", Toast.LENGTH_SHORT).show()
            false
        }
    }


    private fun asignarTarea(tarea: Task) {
        TaskHelper().asignarTarea(tarea)
        Toast.makeText(this, "Tarea asignada a grado ${tarea.grado}: ${tarea.titulo} con fecha de entrega ${tarea.fecha}", Toast.LENGTH_SHORT).show()
    }
}