package com.example.gimnasioalemn_friedrichvonschiller

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.gimnasioalemn_friedrichvonschiller.database.DatabaseHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityMessagesBinding
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityStartBinding
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class  Messages : AppCompatActivity() { 
    private lateinit var binding: ActivityMessagesBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper()// Inicializar DatabaseHelper
        //initComponents()

        //Traer datos
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userRole = sharedPreferences.getString("USER_ROL", null)
        val userId = sharedPreferences.getString("USER_ID", null)
        val userName = sharedPreferences.getString("USER_NAME", null)
        val userEmail = sharedPreferences.getString("USER_EMAIL", null)
        val userPhoneNumber = sharedPreferences.getString("USER_PHONE_NUMBER", null)

        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
       // navigationBarHelper.setupNavigationBar(binding.root)

    }

    //private fun initComponents() {
      //  LoadImgagesInScrollViews()
       // binding.btnClassSchedule.setOnClickListener {
        //}
        //binding.btnMessage.setOnClickListener {
          //  navigateToMessages()
        //}
        //binding.btnMyTasks.setOnClickListener {
          //  navigateToMyTasks()
        //}
        //binding.btnStudentTracking.setOnClickListener {
          //  navigateToStudentTracking()
        //}
    //}

}