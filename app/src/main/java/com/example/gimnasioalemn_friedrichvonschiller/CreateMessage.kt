package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gimnasioalemn_friedrichvonschiller.database.MessagesHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.FragmentCreateMessageBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Message

class CreateMessage : Fragment() {
    private lateinit var binding: FragmentCreateMessageBinding

    private val grados = arrayOf(
        "Parvulos", "Prekinder", "Kinder", "Kl1", "Kl2", "Kl3", "Kl4", "Kl5",
        "Kl6", "Kl7", "Kl8", "Kl9", "Kl10", "Kl11", "Kl12"
    )

    private var userName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateMessageBinding.inflate(inflater, container, false)

        // Traer datos
        val sharedPreferences = requireContext().getSharedPreferences("USER_PREFS", Context.MODE_PRIVATE)
        userName = sharedPreferences.getString("USER_NAME", null)

        initComponents()
        return binding.root
    }

    private fun initComponents() {
        // Configurar spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, grados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGrado.adapter = adapter

        binding.btnGuardar.setOnClickListener {
            saveMessage()
        }
    }

    private fun saveMessage() {
        val gradoSeleccionado = binding.spinnerGrado.selectedItem.toString()
        val nombreMensaje = binding.etTittleMessage.text.toString().trim()
        val descripcionMensaje = binding.etDescriptionMenssage.text.toString().trim()

        if (nombreMensaje.isEmpty()) {
            binding.etTittleMessage.error = "Ingresa el nombre del mensaje"
            binding.etTittleMessage.requestFocus()
            return
        }

        if (descripcionMensaje.isEmpty()) {
            binding.etDescriptionMenssage.error = "Ingresa la descripción del mensaje"
            binding.etDescriptionMenssage.requestFocus()
            return
        }

        val teacherName = userName ?: "Profesor Desconocido"

        val message = Message(
            title = nombreMensaje,
            date = getCurrentDateString(),
            teacher = teacherName,
            grade = gradoSeleccionado,
            description = descripcionMensaje
        )

        val messagesHelper = MessagesHelper()
        messagesHelper.saveMessage(message,
            onSuccess = {
                Toast.makeText(requireContext(), "Mensaje guardado correctamente", Toast.LENGTH_SHORT).show()
                binding.etTittleMessage.text?.clear()
                binding.etDescriptionMenssage.text?.clear()
                binding.spinnerGrado.setSelection(0)
            },
            onFailure = { error ->
                Toast.makeText(requireContext(), "Error al guardar: $error", Toast.LENGTH_SHORT).show()
            })
    }

    private fun getCurrentDateString(): String {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return sdf.format(java.util.Date())
    }
}