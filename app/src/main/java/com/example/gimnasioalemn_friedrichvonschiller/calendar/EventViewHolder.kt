package com.example.gimnasioalemn_friedrichvonschiller.calendar

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ItemEventBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Event

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemEventBinding.bind(view)
    fun bind(event: Event) {
        binding.tvTitleEvent.text = event.title
        binding.tvDate.text = event.date
        binding.tvDescripcionInforme.text = event.description
        binding.tvHour.text = event.time
    }
}