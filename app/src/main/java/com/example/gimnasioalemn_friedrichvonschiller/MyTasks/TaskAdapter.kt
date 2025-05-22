package com.example.gimnasioalemn_friedrichvonschiller.MyTasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.R
import com.example.gimnasioalemn_friedrichvonschiller.model.Task

class TaskAdapter(private var tasks: List<Task>, private val onTaskChanged: (Task, Boolean) -> Unit)
    : RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position], onTaskChanged)
    }

    override fun getItemCount() = tasks.size

    fun updateList(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }
}