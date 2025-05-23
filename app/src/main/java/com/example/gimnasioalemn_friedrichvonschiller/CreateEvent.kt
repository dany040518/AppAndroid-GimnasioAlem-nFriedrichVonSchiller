package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gimnasioalemn_friedrichvonschiller.database.MessagesHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.FragmentCreateEventBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Message

class CreateEvent : Fragment() {
    private lateinit var binding: FragmentCreateEventBinding

    private val hours = arrayOf(
        "8:00", "8:30", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30",
        "12:00", "12:30", "13:00", "13:30", "14:00", "14:30"
    )

    private var userName: String? = null

    override fun onCreateView(
        inflater: android.view.LayoutInflater, container: android.view.ViewGroup?,
        savedInstanceState: Bundle?,
    ): android.view.View {
        binding = FragmentCreateEventBinding.inflate(inflater, container, false)

        val sharedPreferences = requireContext().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("USER_NAME", null)

        initComponents()
        return binding.root
    }

    private fun initComponents() {
        // Configurar spinner de horas
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hours)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.eventHour.adapter = adapter

        binding.btnSave.setOnClickListener {
            saveEvent()
        }

        binding.btnClose.setOnClickListener {
            // Cierra el fragmento o haz lo que necesites
            parentFragmentManager.popBackStack()
        }
    }

    private fun saveEvent() {
        val selectedHour = binding.eventHour.selectedItem.toString()
        val eventTitle = binding.eventTitle.text.toString().trim()
        val eventDescription = binding.eventDescription.text.toString().trim()

        if (eventTitle.isEmpty()) {
            binding.eventTitle.error = "Ingresa el título del evento"
            binding.eventTitle.requestFocus()
            return
        }

        if (eventDescription.isEmpty()) {
            binding.eventDescription.error = "Ingresa la descripción del evento"
            binding.eventDescription.requestFocus()
            return
        }

        val teacherName = userName ?: "Profesor Desconocido"

        val message = Message(
            title = eventTitle,
            date = getCurrentDateString(),
            teacher = teacherName,
            grade = null,  // Asumo que evento no tiene grado, si necesitas, agrega otro campo o lo pasas
            description = eventDescription
        )

        val messagesHelper = MessagesHelper()
        messagesHelper.saveMessage(message,
            onSuccess = {
                Toast.makeText(requireContext(), "Evento guardado correctamente", Toast.LENGTH_SHORT).show()
                binding.eventTitle.text?.clear()
                binding.eventDescription.text?.clear()
                binding.eventHour.setSelection(0)
            },
            onFailure = { error ->
                Toast.makeText(requireContext(), "Error al guardar: $error", Toast.LENGTH_SHORT).show()
            })
    }

    private fun getCurrentDateString(): String {
        val sdf = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}