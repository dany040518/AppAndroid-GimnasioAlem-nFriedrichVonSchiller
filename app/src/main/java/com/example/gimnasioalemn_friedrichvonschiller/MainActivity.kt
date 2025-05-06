package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //aqui se inicializan los componentes de la vista
    private lateinit var binding: ActivityMainBinding

    //se crea la vista
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initComponets()
    }

    //se inicializan los componentes de la vista
    private fun initComponets() {
        binding.btnAcceder.setOnClickListener {
            navigateToLogIn()
        }
    }

    //se navega a la vista de logIn
    private fun navigateToLogIn() {
        val intent = Intent(this, logIn::class.java)
        startActivity(intent)
    }

}