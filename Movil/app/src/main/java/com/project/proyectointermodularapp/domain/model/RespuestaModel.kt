package com.project.proyectointermodularapp.domain.model

data class RespuestaModel(
    val id: Int,
    val solicitudId: Int,
    val empresaId: Int,
    val clienteId: Int,
    val estado: EstadoRespuesta,
    val contenido: String,
    val comentarios: List<ComentarioRespuestaModel> = emptyList(),
    val fechaHora: String
)
