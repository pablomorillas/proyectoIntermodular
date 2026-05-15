package com.project.proyectointermodularapp.domain.model

data class ComentarioSolicitudModel(
    val id: Int,
    val solicitudId: Int,
    val autor: String,
    val contenido: String,
    val fechaHora: String,
    val respuestas: List<ComentarioSolicitudModel> = emptyList()
)
