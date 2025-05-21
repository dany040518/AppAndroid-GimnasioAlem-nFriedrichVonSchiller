package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityLogInBinding
import com.example.gimnasioalemn_friedrichvonschiller.database.DatabaseHelper

class logIn : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar DatabaseHelper
        dbHelper = DatabaseHelper()

        // Configurar el botón de login
        initComponents()
    }

    private fun initComponents() {
        binding.btnIngresar.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val userId = binding.etUser.text.toString().trim()
        val password = binding.etPassword.text.toString()

        if (userId.isEmpty() || password.isEmpty()) {
            Toast.makeText(applicationContext, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Consultar la base de datos usando DatabaseHelper
        dbHelper.getUserData(userId, object : DatabaseHelper.UserDataCallback {
            override fun onSuccess(fullName: String, role: String, storedPassword: String, email: String, phoneNumber: String) {

                if (storedPassword == password) {// Validar el password
                    if (role == "student") {
                        Toast.makeText(applicationContext, "Acceso exitoso como Estudiante", Toast.LENGTH_SHORT).show()
                        saveData(role, userId, fullName, email, phoneNumber)
                        navigateToStartStudents(userId)
                    } else if (role == "teacher") {
                        Toast.makeText(applicationContext, "Acceso exitoso como Docente", Toast.LENGTH_SHORT).show()
                        saveData(role, userId, fullName, email, phoneNumber)
                        navigateToStartTeachers(userId)
                    } else if (role == "admin") {
                        Toast.makeText(applicationContext, "Acceso exitoso como Administrador", Toast.LENGTH_SHORT).show()
                        //saveData(userId, fullName, email, phoneNumber)
                        //navigateToStartAdmin()
                    }
                    finish()
                } else {
                    // Contraseña incorrecta
                    Toast.makeText(applicationContext, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(errorMessage: String) {
                // Si el usuario no es encontrado
                Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun navigateToStartStudents(userId: String) {
        val intent = Intent(this, Start::class.java)
        startActivity(intent)
    }
    private fun navigateToStartTeachers(userId: String) {
        val intent = Intent(this, Start::class.java)
        startActivity(intent)
    }

    private fun saveData(userRole:String, userId: String, fullName: String, email: String, phoneNumber: String){
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("USER_ROL", userRole)
        editor.putString("USER_ID", userId)
        editor.putString("USER_NAME", fullName)
        editor.putString("USER_EMAIL", email)
        editor.putString("USER_PHONE_NUMBER", phoneNumber)
        editor.apply()
    }
}