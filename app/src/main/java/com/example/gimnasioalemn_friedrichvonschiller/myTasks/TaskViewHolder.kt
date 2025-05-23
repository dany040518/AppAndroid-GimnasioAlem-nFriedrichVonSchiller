package com.example.gimnasioalemn_friedrichvonschiller.myTasks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ItemTaskBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Task

class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemTaskBinding.bind(view)
    fun bind(tarea: Task, onTaskChanged: (Task, Boolean) -> Unit) {
        binding.tvTituloTarea.text = tarea.titulo
        binding.tvFecha.text = tarea.fecha
        binding.tvAsignatura.text = tarea.asignatura
        binding.cbStatus.isChecked = tarea.estado=="completada"
        binding.cbStatus.setOnCheckedChangeListener { _, isChecked ->
            onTaskChanged(tarea, isChecked)
        }
    }
}