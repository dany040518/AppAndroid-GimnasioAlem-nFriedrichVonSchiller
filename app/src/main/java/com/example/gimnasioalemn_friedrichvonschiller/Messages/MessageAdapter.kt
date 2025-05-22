package com.example.gimnasioalemn_friedrichvonschiller.Messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ItemMessageBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Message

class MessageAdapter(
    private val messages: List<Message>
) : RecyclerView.Adapter<MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        // Inflar el layout para el item
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        // Obtener el mensaje en esa posición y asociarlo al ViewHolder
        val message = messages[position]
        holder.bind(message)
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}