package com.example.gimnasioalemn_friedrichvonschiller

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gimnasioalemn_friedrichvonschiller.database.DatabaseHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityProfileBinding
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper()// Inicializar DatabaseHelper
        initComponents()

        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        val userName = sharedPreferences.getString("USER_NAME", null)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)
        val userPhoneNumber = sharedPreferences.getString("USER_PHONE_NUMBER", null)

        binding.userName.text = "$userName"
        binding.userId.text = "$userId"
        binding.userEmail.text = "$userEmail"
        binding.userPhoneNumber.text = "$userPhoneNumber"

        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)
    }

    private fun initComponents() {
        // Mostrar el overlay cuando se hace clic en el botón Datos Personales
        binding.btnDataForms.setOnClickListener {
            val openDataForm = findViewById<ConstraintLayout>(R.id.openDataForm)
            openDataForm.visibility = View.VISIBLE  // Mostramos el overlay
        }

        // Cuando se hace clic en el botón cerrar, ocultamos el overlay
        val btnCloseDataForm = findViewById<Button>(R.id.btnCloseDataForm)
        btnCloseDataForm.setOnClickListener {
            val openDataForm = findViewById<ConstraintLayout>(R.id.openDataForm)
            openDataForm.visibility = View.GONE  // Ocultamos el overlay
        }
    }
}