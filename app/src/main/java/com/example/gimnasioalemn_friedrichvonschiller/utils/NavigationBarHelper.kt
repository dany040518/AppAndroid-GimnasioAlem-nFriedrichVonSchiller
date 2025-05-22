package com.example.gimnasioalemn_friedrichvonschiller.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gimnasioalemn_friedrichvonschiller.MainActivity
import com.example.gimnasioalemn_friedrichvonschiller.Start
import com.example.gimnasioalemn_friedrichvonschiller.Profile
import com.example.gimnasioalemn_friedrichvonschiller.databinding.FragmentMenuNavigationBarBinding

class NavigationBarHelper(private val context: Context) {

    fun setupNavigationBar(view: ConstraintLayout) {
        val binding = FragmentMenuNavigationBarBinding.bind(view)

        binding.btnInicio.setOnClickListener {
            navigateToStart()
        }

        binding.btnCalendario.setOnClickListener {
            navigateToCalendar()
        }

        binding.btnPerfil.setOnClickListener {
            navigateToProfile()
        }

        binding.btnSalir.setOnClickListener {
            logout()
        }
    }

    private fun navigateToStart() {
        val intent = Intent(context, Start::class.java)
        context.startActivity(intent)
    }

    private fun navigateToCalendar() {
        /*val intent = Intent(context, Calendar::class.java)
        context.startActivity(intent)*/
    }

    private fun navigateToProfile() {
        val intent = Intent(context, Profile::class.java)
        context.startActivity(intent)
    }

    private fun logout() {
        Toast.makeText(context, "Sesion cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }
}