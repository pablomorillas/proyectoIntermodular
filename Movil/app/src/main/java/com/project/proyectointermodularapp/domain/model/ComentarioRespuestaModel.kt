package com.project.proyectointermodularapp.domain.model

data class ComentarioRespuestaModel(
    val id: Int,
    val respuestaId: Int,
    val autor: String,
    val contenido: String,
    val fechaHora: String,
    val respuestas: List<ComentarioRespuestaModel> = emptyList()
)
