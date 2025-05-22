package com.example.gimnasioalemn_friedrichvonschiller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.gimnasioalemn_friedrichvonschiller.databinding.FragmentCreateMessageBinding

class CreateMessage : Fragment() {
    private lateinit var binding: FragmentCreateMessageBinding

    private val grados = arrayOf(
        "Parvulos", "Prekinder", "Kinder", "Kl1", "Kl2", "Kl3", "Kl4", "Kl5",
        "Kl6", "Kl7", "Kl8", "Kl9", "Kl10", "Kl11", "Kl12"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreateMessageBinding.inflate(inflater, container, false)
        initComponents()
        return binding.root
    }

    private fun initComponents() {
        // Configurar spinner
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, grados)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGrado.adapter = adapter

        binding.btnGuardar.setOnClickListener {
            val gradoSeleccionado = binding.spinnerGrado.selectedItem.toString()
            val nombreMensaje = binding.etNombreMensaje.text.toString().trim()
            val descripcionMensaje = binding.etDescripcionMensaje.text.toString().trim()

            if (nombreMensaje.isEmpty()) {
                binding.etNombreMensaje.error = "Ingresa el nombre del mensaje"
                binding.etNombreMensaje.requestFocus()
                return@setOnClickListener
            }

            if (descripcionMensaje.isEmpty()) {
                binding.etDescripcionMensaje.error = "Ingresa la descripción del mensaje"
                binding.etDescripcionMensaje.requestFocus()
                return@setOnClickListener
            }

            Toast.makeText(requireContext(),
                "Guardado: $gradoSeleccionado - $nombreMensaje",
                Toast.LENGTH_SHORT).show()

            binding.etNombreMensaje.text?.clear()
            binding.etDescripcionMensaje.text?.clear()
            binding.spinnerGrado.setSelection(0)
        }
    }
}