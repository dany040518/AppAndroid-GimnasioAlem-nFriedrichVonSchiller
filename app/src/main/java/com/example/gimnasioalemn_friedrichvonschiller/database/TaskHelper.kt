package com.example.gimnasioalemn_friedrichvonschiller.database

import android.util.Log
import com.example.gimnasioalemn_friedrichvonschiller.model.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class TaskHelper {

    fun asignarTarea(tarea: Task) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        // 1. Obtener todos los usuarios con grade_asigned == grado
        val query = usersRef.orderByChild("grade").equalTo(tarea.grado)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val userId = userSnapshot.key ?: continue
                        usersRef.child(userId).child("tasks").push().setValue(tarea)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("TAG", "Error al obtener usuarios: ${error.message}")
            }
        })
    }

}