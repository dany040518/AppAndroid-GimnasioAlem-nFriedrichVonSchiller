package com.example.gimnasioalemn_friedrichvonschiller.studentTracking

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.gimnasioalemn_friedrichvonschiller.databinding.ItemFollowUpBinding
import com.example.gimnasioalemn_friedrichvonschiller.model.Annotation


class TrackingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemFollowUpBinding.bind(view)
    fun bind(anotacion: Annotation) {
        binding.tvFecha.text = anotacion.date
        binding.tvTipoInforme.text = anotacion.type
        binding.tvDescripcionInforme.text = anotacion.description
        binding.tvNombreDocente.text = anotacion.teacher
    }
}