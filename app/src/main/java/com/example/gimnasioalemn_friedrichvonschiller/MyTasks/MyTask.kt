package com.example.gimnasioalemn_friedrichvonschiller.MyTasks

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gimnasioalemn_friedrichvonschiller.database.TaskHelper
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ActivityMyTaskBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Task

class MyTask : AppCompatActivity() {

    private lateinit var binding : ActivityMyTaskBinding
    private lateinit var adapter: TaskAdapter
    private lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTasks()
    }

    private fun initUI() {
        adapter = TaskAdapter(tasks,
            onTaskChanged = { task, isChecked ->
                modifyTask(task, isChecked)
            }
        )
        binding.tareasList.layoutManager = LinearLayoutManager(this)
        binding.tareasList.adapter = adapter
    }

    private fun getTasks() {
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        TaskHelper().obtenerTareasEstudiante(userId.toString()) { tareas ->
            // Aquí recibes la lista de tareas del estudiante con userId 1032676704
            if (tareas.isEmpty())Log.d("Tareas", "No se encontraron tareas")
            else{
                tasks = tareas as ArrayList<Task>
                initUI()
            }
        }
    }

    private fun modifyTask(task: Task, isChecked: Boolean) {
        val taskHelper = TaskHelper()
        val sharedPreferences = getSharedPreferences("USER_PREFS", MODE_PRIVATE)
        val userId = sharedPreferences.getString("USER_ID", null)
        task.estado = if (isChecked) "completada" else "pendiente"
        taskHelper.actualizarTarea(userId.toString(), task)
    }
}