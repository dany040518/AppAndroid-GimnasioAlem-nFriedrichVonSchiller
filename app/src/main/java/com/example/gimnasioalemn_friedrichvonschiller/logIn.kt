package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityLogInBinding
import com.google.firebase.auth.FirebaseAuth

class logIn : AppCompatActivity() {
    //aqui se inicializan los componentes de la vista
    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth

    //se crea la vista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        initComponets()
    }

    //se inicializan los componentes de la vista
    private fun initComponets() {
        binding.btnIngresar.setOnClickListener {
            login()
        }
    }

    //se realiza el login
    private fun login() {
        val user = binding.etUser.text.toString()
        val password = binding.etPassword.text.toString()

        //se valida que los campos no esten vacios
        if (user.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Por favor, completa todos los campos",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        //se realiza el login
        auth.signInWithEmailAndPassword(user, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Acceso exitoso", Toast.LENGTH_SHORT).show()
                    navigateToStart()
                    finish()
                } else {
                    val errorMessage = task.exception?.message ?: "Acceso fallido"
                    Toast.makeText(applicationContext, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

    //se navega a la vista de inicio
    private fun navigateToStart() {
        val intent = Intent(this, start::class.java)
        startActivity(intent)
    }

}