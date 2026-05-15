package com.project.proyectointermodularapp.domain.model

data class SolicitudModel(
    val id: Int,
    val clienteId: Int,
    val titulo: String,
    val contenido: String,
    val fechaHora: String,
    val imagenes: List<String> = emptyList(),
    val comentarios: List<ComentarioSolicitudModel> = emptyList(),
    val privada: Boolean = false
)
