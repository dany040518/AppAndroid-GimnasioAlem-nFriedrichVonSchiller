package com.example.gimnasioalemn_friedrichvonschiller.calendar

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gimnasioalemn_friedrichvonschiller.R
import com.example.gimnasioalemn_friedrichvonschiller.database.EventHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityCalendarBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Event
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Calendar : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private var selectedDate = getCurrentDate()
    private var events: List<Event> = emptyList()
    private lateinit var adapter: EventAdapter
    private var userRole: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        userRole = sharedPreferences.getString("USER_ROL", null)

        getEvents()
        initUI()
        initComponents()
    }

    private fun initUI() {
        adapter = EventAdapter(events)
        binding.rvEvents.layoutManager = LinearLayoutManager(this)
        binding.rvEvents.adapter = adapter

        binding.btnAddEvent.isVisible = userRole == "admin"
        binding.btnAddEvent.isEnabled = userRole == "admin"
    }

    private fun initComponents() {
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedMonth = month + 1
            selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, selectedMonth, year)
            getEvents(selectedDate)
        }
        binding.btnAddEvent.setOnClickListener {
            showAddDialog()
        }
    }

    private fun getEvents(date: String = getCurrentDate()) {
        EventHelper().getEventsByDate(date,
            onResult = { eventList ->
                events = eventList
                initUI()
                Log.i("Calendar", "Eventos obtenidos: ${events.size} en la fecha $date")
            },
            onError = { errorMessage ->
                Log.e("Calendar", "Error al obtener eventos: $errorMessage")
            })
    }

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun showAddDialog() {
        // Infla el layout personalizado
        val dialogView = layoutInflater.inflate(R.layout.fragment_create_event, null)

        // Opcional: obtener referencias a EditTexts y botones del diálogo
        val tituloEvento = dialogView.findViewById<EditText>(R.id.eventTitle)
        val horaEvento = dialogView.findViewById<EditText>(R.id.eventHour)
        val descripcionEvento = dialogView.findViewById<EditText>(R.id.eventDescription)
        val btnCerrar = dialogView.findViewById<Button>(R.id.btnClose)
        val btnCrear = dialogView.findViewById<Button>(R.id.btnSave)

        // Construir el AlertDialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Botón cerrar cierra el diálogo
        btnCerrar.setOnClickListener {
            dialog.dismiss()
        }

        // Botón crear recoge datos y hace lo que necesites
        btnCrear.setOnClickListener {
            val titulo = tituloEvento.text.toString().trim()
            val hora = horaEvento.text.toString().trim()
            val descripcion = descripcionEvento.text.toString().trim()

            // Validaciones si quieres
            if (titulo.isEmpty() || hora.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val evento = Event(titulo, selectedDate, descripcion, hora)
            saveEvent(evento)
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun saveEvent(event: Event) {
        EventHelper().saveEvent(event,
            onSuccess = {
                Log.i("Calendar", "Evento guardado correctamente")
                getEvents(selectedDate)
            },
            onFailure = { errorMessage ->
                Log.e("Calendar", "Error al guardar evento: $errorMessage")
            })
    }

}