package com.project.proyectointermodularapp.domain.model

data class ClienteModel(
    val id: Int,
    val username: String,
    val email: String,
    val direccion: String,
    val password: String,
    val solicitudesIds: List<Int> = emptyList()
)
