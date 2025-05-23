package com.example.gimnasioalemn_friedrichvonschiller.studentTracking

import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.model.Annotation


class TrackingAdapter(annotations: List<Annotation>): RecyclerView.Adapter<TrackingViewHolder>() {

    private var annotations: List<Annotation> = annotations

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): TrackingViewHolder {
        val layoutInflater = android.view.LayoutInflater.from(parent.context)
        return TrackingViewHolder(layoutInflater.inflate(com.example.gimnasioalemn_friedrichvonschiller.R.layout.item_follow_up, parent, false))
    }

    override fun onBindViewHolder(holder: TrackingViewHolder, position: Int) {
        holder.bind(annotations[position])
    }

    override fun getItemCount()= annotations.size
}