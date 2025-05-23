package com.example.gimnasioalemn_friedrichvonschiller.profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityProfileBinding
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class Profile : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserData()
        setupNavigationBar()
        setupListeners()
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        binding.userName.text = sharedPreferences.getString("USER_NAME", "")
        binding.userId.text = sharedPreferences.getString("USER_ID", "")
        binding.userEmail.text = sharedPreferences.getString("USER_EMAIL", "")
        binding.userPhoneNumber.text = sharedPreferences.getString("USER_PHONE_NUMBER", "")
    }

    private fun setupNavigationBar() {
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)
    }

    private fun setupListeners() {
        binding.btnDataForms.setOnClickListener {
            showDataForm()
        }
    }

    private fun showDataForm() {
        binding.containerDataForm.visibility = View.VISIBLE
        binding.root.isEnabled = false

        supportFragmentManager.beginTransaction()
            .replace(binding.containerDataForm.id, DataForm())
            .addToBackStack(null)
            .commit()
    }
}