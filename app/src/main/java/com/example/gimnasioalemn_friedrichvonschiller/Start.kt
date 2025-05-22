package com.example.gimnasioalemn_friedrichvonschiller

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.gimnasioalemn_friedrichvonschiller.MyTasks.MyTask
import com.example.gimnasioalemn_friedrichvonschiller.database.DatabaseHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityStartBinding
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper

class Start : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
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
        binding.userName.text = "$userName"

        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)

        //Configuracion gestion por roles
        binding.TeacherManagement.isVisible= userRole == "teacher"
        binding.TeacherManagement.isEnabled= userRole == "teacher"

        binding.StudentsManagement.isVisible= userRole == "student"
        binding.StudentsManagement.isEnabled= userRole == "student"


    }

    private fun initComponents() {
        LoadImgagesInScrollViews()
        binding.btnClassSchedule.setOnClickListener {
        }
        binding.btnMessage.setOnClickListener {
            navigateToMessages()
        }
        binding.btnMyTasks.setOnClickListener {
            navigateToMyTasks()
        }
        binding.btnStudentTracking.setOnClickListener {
            navigateToStudentTracking()
        }
        binding.btnAssignTasks.setOnClickListener {
            navigateToAssignTasks()
        }
        binding.btnAnnotationsAndFollowUp.setOnClickListener {
            navigateToAnnotationsAndFollowUp()
        }
    }

    private fun getImagesGalery(): List<String> {
        return listOf(
            "https://gimnasioaleman.edu.co/wp-content/uploads/2024/10/Home_Mision-1024x681.webp",
            "https://gimnasioaleman.edu.co/wp-content/uploads/2024/10/Home_Vision-1024x681.webp",
            "https://gimnasioaleman.edu.co/wp-content/uploads/2024/10/Comedor-Gimnasio-Aleman-FVS.webp",
            "https://gimnasioaleman.edu.co/wp-content/uploads/2024/10/Nuestro-equippo-en-GA.webp",
            "https://th.bing.com/th/id/OIP.1ksS73fxMbDpDbvnUVeFmwHaE7?cb=iwc1&rs=1&pid=ImgDetMain"
        )
    }

    private fun LoadImgagesInScrollViews() {
        val imageUrlsHorror = getImagesGalery()
        val linearLayoutImagesGalery = binding.linearLayoutImagesGalery
        addImagesToLayout(imageUrlsHorror, linearLayoutImagesGalery)//Agregar las imagenes
    }

    // Función auxiliar para agregar imágenes a un LinearLayout
    private fun addImagesToLayout(imageUrls: List<String>, layout: LinearLayout) {
        for (imageUrl in imageUrls) {
            val imageView = ImageView(this)
            val params = LinearLayout.LayoutParams(
                600, // Ancho fijo
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            imageView.layoutParams = params

            // Ajustar la imagen al ancho de la pantalla
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP

            // Usar Glide para cargar la imagen
            Glide.with(this)
                .load(imageUrl)
                .override(800, 800) // Establecer dimensiones fijas
                .into(imageView)

            // Añadir el ImageView al LinearLayout
            layout.addView(imageView)
        }
    }

    private fun navigateToMessages() {
        val intent = Intent(this, Messages::class.java)
        startActivity(intent)
    }

    private fun navigateToMyTasks() {
        val intent = Intent(this, MyTask::class.java)
        startActivity(intent)
    }

    private fun navigateToStudentTracking() {
        val intent = Intent(this, StudentTracking::class.java)
        startActivity(intent)
    }

    private fun navigateToAssignTasks(){
        val intent = Intent(this, AssignTasks::class.java)
        startActivity(intent)
    }

    private fun navigateToAnnotationsAndFollowUp(){
        val intent = Intent(this, AnnotationsAndFollowUp::class.java)
        startActivity(intent)
    }
}