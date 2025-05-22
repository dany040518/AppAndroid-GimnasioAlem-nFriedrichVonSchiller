package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.gimnasioalemn_friedrichvonschiller.database.DatabaseHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityMessagesBinding
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class Messages : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper()// Inicializar DatabaseHelper
        initComponents()

        //Traer datos
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userRole = sharedPreferences.getString("USER_ROL", null)
        val userId = sharedPreferences.getString("USER_ID", null)
        val userName = sharedPreferences.getString("USER_NAME", null)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)
        val userPhoneNumber = sharedPreferences.getString("USER_PHONE_NUMBER", null)

        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)

        //Configuracion gestion por roles
        binding.btnMessage.isVisible = userRole == "teacher"
        binding.btnMessage.isEnabled = userRole == "teacher"

        binding.tvNameGrade.isVisible = userRole == "student"
        binding.tvNameGrade.isEnabled = userRole == "student"
    }

    private fun initComponents() {
        binding.btnMessage.setOnClickListener {
            // Mostrar el fragmento
            showCreateMessageFragment()
        }
    }

    private fun showCreateMessageFragment() {
        // Hacer visible el contenedor
        binding.containerCreateMessage.visibility = View.VISIBLE

        // Bloquear interacción con la Activity debajo (opcional)
        binding.root.isEnabled = false

        // Agregar fragmento
        supportFragmentManager.beginTransaction()
            .replace(binding.containerCreateMessage.id, CreateMessage())
            .addToBackStack(null) // Permite cerrar el fragmento con el botón Atrás
            .commit()
    }



}