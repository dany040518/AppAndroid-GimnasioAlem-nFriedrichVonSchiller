package com.example.gimnasioalemn_friedrichvonschiller.StudentTracking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gimnasioalemn_friedrichvonschiller.database.StudentHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityStudentTrackingBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Annotation
import com.example.gimnasioalemn_friedrichvonschiller.utils.NavigationBarHelper


class StudentTracking : AppCompatActivity() {

    private lateinit var binding: ActivityStudentTrackingBinding
    private lateinit var adapter: TrackingAdapter
    private lateinit var anotaciones: ArrayList<Annotation>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentTrackingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getAnotaciones()
        initUI()
    }

    private fun initUI() {
        // Configurar el menú de navegación
        val navigationBarHelper = NavigationBarHelper(this)
        navigationBarHelper.setupNavigationBar(binding.root)

        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val grado = sharedPreferences.getString("USER_GRADE", null).toString()
        binding.tvGrado.text = grado
    }

    private fun initAdapter(){
        adapter = TrackingAdapter(anotaciones)
        binding.seguimientoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.seguimientoRecyclerView.adapter = adapter
    }

    private fun getAnotaciones() {
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        StudentHelper().obtenerAnotacionesPorEstudiante(userId.toString()) { anotaciones ->
            if (anotaciones.isEmpty()) Log.d("Anotaciones", "No se encontraron anotaciones")
            else {
                this.anotaciones = anotaciones as ArrayList<Annotation>
                initAdapter()
            }
        }
    }
}