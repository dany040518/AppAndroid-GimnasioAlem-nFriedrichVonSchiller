package com.example.gimnasioalemn_friedrichvonschiller.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.R
import com.example.gimnasioalemn_friedrichvonschiller.model.Event

class EventAdapter(private val eventList: List<Event>) : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(eventList[position])
    }

    override fun getItemCount()= eventList.size

    fun updateEvents(newEvents: List<Event>) {
        (eventList as ArrayList).clear()
        eventList.addAll(newEvents)
        notifyDataSetChanged()
    }
}