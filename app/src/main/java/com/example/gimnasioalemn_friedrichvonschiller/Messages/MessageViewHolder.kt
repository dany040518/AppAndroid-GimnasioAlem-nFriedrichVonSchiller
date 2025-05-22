package com.example.gimnasioalemn_friedrichvonschiller.Messages


import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ItemMessageBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Message

class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(message: Message) {
        // Rellenar los datos del mensaje
        binding.tvNameActivity.text = message.title
        binding.tvDate.text = message.date
        binding.tvActivityDescription.text = message.description
        binding.tvNameTeacherAnnouncement.text = message.teacher
        binding.tvGrade.text = message.grade
    }
}
