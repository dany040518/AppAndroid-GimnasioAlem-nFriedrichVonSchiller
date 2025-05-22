package com.example.gimnasioalemn_friedrichvonschiller.model

data class Task (
    var titulo: String? = "",
    var fecha: String? = "",
    var asignatura: String? = "",
    var grado: String? = "",
    var estado: String? = "pendiente",
    var id: String = ""
)