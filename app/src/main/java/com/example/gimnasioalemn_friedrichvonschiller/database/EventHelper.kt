package com.example.gimnasioalemn_friedrichvonschiller.database

import com.example.gimnasioalemn_friedrichvonschiller.model.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventHelper {
    fun getEventsByDate(date: String, onResult: (List<Event>) -> Unit, onError: (String) -> Unit) {
        val database = FirebaseDatabase.getInstance().getReference("events")
        database.orderByChild("date").equalTo(date)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val eventsList = mutableListOf<Event>()
                    for (child in snapshot.children) {
                        val event = child.getValue(Event::class.java)
                        if (event != null) {
                            eventsList.add(event)
                        }
                    }
                    onResult(eventsList)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.message)
                }
            })
    }

    fun saveEvent(event: Event, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val database = FirebaseDatabase.getInstance().getReference("events")
        val key = database.push().key
        if (key == null) {
            onFailure("Error al generar ID para el evento")
            return
        }

        database.child(key).setValue(event)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Error desconocido") }
    }


}