package com.example.gimnasioalemn_friedrichvonschiller.database

import com.example.gimnasioalemn_friedrichvonschiller.model.Message
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MessagesHelper {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("messages")

    fun saveMessage(message: Message, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val key = database.push().key
        if (key == null) {
            onFailure("Error al generar ID para el mensaje")
            return
        }
        database.child(key).setValue(message)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e.message ?: "Error desconocido") }
    }

    fun getMessagesByGrade(grade: String, onResult: (List<Message>) -> Unit, onError: (String) -> Unit) {
        database.orderByChild("grade").equalTo(grade)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()
                    for (child in snapshot.children) {
                        val msg = child.getValue(Message::class.java)
                        if (msg != null) messages.add(msg)
                    }
                    onResult(messages)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.message)
                }
            })
    }

    fun getMessagesByTeacher(teacher: String, onResult: (List<Message>) -> Unit, onError: (String) -> Unit) {
        database.orderByChild("teacher").equalTo(teacher)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messages = mutableListOf<Message>()
                    for (child in snapshot.children) {
                        val msg = child.getValue(Message::class.java)
                        if (msg != null) messages.add(msg)
                    }
                    onResult(messages)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error.message)
                }
            })
    }
}
