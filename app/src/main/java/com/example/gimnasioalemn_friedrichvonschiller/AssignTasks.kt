package com.example.gimnasioalemn_friedrichvonschiller

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.database.TaskHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityAssignTasksBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AssignTasks : AppCompatActivity() {

    private lateinit var binding: ActivityAssignTasksBinding

    private var userName: String? = null
    private var userId: String? = null
    private var subjects: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssignTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("USER_NAME", null)
        userId = sharedPreferences.getString("USER_ID", null)
        subjects = sharedPreferences.getString("SUBJECTS", null)

        initUI()
        initComponents()
    }

    private fun initUI() {
        val grados = arrayOf(
            "Parvulos", "Prekinder", "Kinder", "Kl1", "Kl2", "Kl3", "Kl4",
            "Kl5", "Kl6", "Kl7", "Kl8", "Kl9", "Kl10", "Kl11", "Kl12"
        )
        val adapterGrado = ArrayAdapter(this, android.R.layout.simple_spinner_item, grados)
        adapterGrado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGrado.adapter = adapterGrado

        val subjectsList = subjects?.split(",")?.map { it.trim() } ?: emptyList()
        val adapterAsignatura = ArrayAdapter(this, android.R.layout.simple_spinner_item, subjectsList)
        adapterAsignatura.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAsignatura.adapter = adapterAsignatura
    }

    private fun initComponents() {
        binding.btnAsignarTarea.setOnClickListener {
            validateParams()
        }
        binding.etFechaEntrega.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%d", selectedDayOfMonth, selectedMonth + 1, selectedYear)
                binding.etFechaEntrega.setText(fechaSeleccionada)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun validateParams() {
        val grado = binding.spinnerGrado.selectedItem?.toString() ?: ""
        val titulo = binding.etTitulo.text.toString().trim()
        val fecha = binding.etFechaEntrega.text.toString().trim()
        val asignatura = binding.spinnerAsignatura.selectedItem?.toString() ?: ""

        if (titulo.isEmpty() || grado.isEmpty() || fecha.isEmpty() || asignatura.isEmpty()) {
            Toast.makeText(this, "Por favor, llene todos los campos.", Toast.LENGTH_SHORT).show()
            return
        }

        if (validateDate(fecha)) {
            asignarTarea(Task(titulo, fecha, asignatura, grado))
        } else {
            Toast.makeText(this, "La fecha de entrega no puede ser anterior a hoy.", Toast.LENGTH_SHORT).show()
        }
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
            !fechaSeleccionada.before(hoy)
        } catch (e: Exception) {
            Toast.makeText(this, "Formato de fecha incorrecto.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun asignarTarea(tarea: Task) {
        TaskHelper().asignarTarea(tarea)
        Toast.makeText(
            this,
            "Tarea asignada a grado ${tarea.grado}: ${tarea.titulo} con fecha de entrega ${tarea.fecha}",
            Toast.LENGTH_SHORT
        ).show()
    }
}