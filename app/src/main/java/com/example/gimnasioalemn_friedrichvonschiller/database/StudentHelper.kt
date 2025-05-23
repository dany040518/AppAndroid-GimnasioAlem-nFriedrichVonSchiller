package com.example.gimnasioalemn_friedrichvonschiller.database

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.example.gimnasioalemn_friedrichvonschiller.model.Annotation


class StudentHelper {

    fun cargarEstudiantesPorGrado(grado: String, callback: (List<String>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val usersRef = database.getReference("users")

        val estudiantesIds = mutableListOf<String>()

        val query = usersRef.orderByChild("grade").equalTo(grado)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                estudiantesIds.clear()
                for (userSnapshot in snapshot.children) {
                    val role = userSnapshot.child("role").getValue(String::class.java)
                    if (role == "student") {
                        val userId = userSnapshot.key
                        if (userId != null) {
                            estudiantesIds.add(userId)
                        }
                    }
                }
                callback(estudiantesIds)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Annotations", "Error al cargar estudiantes: ${error.message}")
                callback(emptyList())
            }
        })
    }

    fun guardarAnotacion(annotation: Annotation, studentId: String, callback: (Boolean) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users").child(studentId).child("student_tracking").push()

        ref.setValue(annotation)
            .addOnSuccessListener {
                callback(true) // guardado exitoso
            }
            .addOnFailureListener { error ->
                Log.e("StudentHelper", "Error al guardar anotación: ${error.message}")
                callback(false) // fallo al guardar
            }
    }

    fun obtenerAnotacionesPorEstudiante(id:String, callback: (List<Annotation>)-> Unit){
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users").child(id).child("student_tracking")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val anotacionesList = mutableListOf<Annotation>()
                for (anotacionSnapshot in snapshot.children) {
                    val anotacion = anotacionSnapshot.getValue(Annotation::class.java)
                    anotacion?.let { anotacionesList.add(it) }
                }
                callback(anotacionesList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("TaskHelper", "Error al obtener anotaciones: ${error.message}")
                callback(emptyList())
            }
        })
    }
}