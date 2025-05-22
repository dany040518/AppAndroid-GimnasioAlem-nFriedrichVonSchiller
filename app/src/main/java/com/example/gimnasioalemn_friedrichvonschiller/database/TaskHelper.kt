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

        // 1. Obtener todos los usuarios con grade == grado
        val query = usersRef.orderByChild("grade").equalTo(tarea.grado)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val userId = userSnapshot.key ?: continue
                        // Crear referencia para push y obtener key
                        val taskRef = usersRef.child(userId).child("tasks").push()
                        val taskId = taskRef.key ?: continue

                        // Crear copia con id asignado
                        val tareaConId = tarea.copy(id = taskId)

                        taskRef.setValue(tareaConId)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("TAG", "Error al obtener usuarios: ${error.message}")
            }
        })
    }

    fun obtenerTareasEstudiante(userId: String, callback: (List<Task>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val tasksRef = database.getReference("users").child(userId).child("tasks")

        tasksRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tareasList = mutableListOf<Task>()
                for (taskSnapshot in snapshot.children) {
                    val tarea = taskSnapshot.getValue(Task::class.java)
                    tarea?.let { tareasList.add(it) }
                }
                callback(tareasList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TaskHelper", "Error al obtener tareas: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun actualizarTarea(userId: String, tarea: Task) {
        val database = FirebaseDatabase.getInstance()
        val taskRef = database.getReference("users").child(userId).child("tasks").child(tarea.id)

        taskRef.setValue(tarea).addOnFailureListener { error ->
            Log.e("TaskHelper", "Error al actualizar tarea: ${error.message}")
        }
    }

}