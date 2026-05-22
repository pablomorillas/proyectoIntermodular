package com.project.proyectointermodularapp.data.network

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.ComentarioRespuestaModel
import com.project.proyectointermodularapp.domain.model.ComentarioSolicitudModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.EstadoRespuesta
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EntityRefDto(
    @SerialName("id") val id: Int = 0
)

@Serializable
data class ClienteDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("username") val username: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("direccion") val direccion: String = "",
    @SerialName("password") val password: String = "",
    @SerialName("solicitudes") val solicitudes: List<EntityRefDto> = emptyList(),
    @SerialName("solicitudesIds") val solicitudesIds: List<Int> = emptyList()
) {
    fun toDomain(): ClienteModel = ClienteModel(
        id = id,
        username = username,
        email = email,
        direccion = direccion,
        password = password,
        solicitudesIds = solicitudesIds.ifEmpty { solicitudes.map { it.id } }
    )
}

@Serializable
data class EmpresaDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("nombre") val nombre: String = "",
    @SerialName("email") val email: String = "",
    @SerialName("nif") val nif: String = "",
    @SerialName("direccion") val direccion: String = ""
) {
    fun toDomain(): EmpresaModel = EmpresaModel(
        id = id,
        nombre = nombre,
        email = email,
        nif = nif,
        direccion = direccion
    )
}

@Serializable
data class SolicitudDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("clienteId") val clienteId: Int? = null,
    @SerialName("cliente") val cliente: EntityRefDto? = null,
    @SerialName("titulo") val titulo: String = "",
    @SerialName("contenido") val contenido: String = "",
    @SerialName("fechaHora") val fechaHora: String = "",
    @SerialName("imagenes") val imagenes: List<String> = emptyList(),
    @SerialName("comentarios") val comentarios: List<ComentarioSolicitudDto> = emptyList(),
    @SerialName("privada") val privada: Boolean = false
) {
    fun toDomain(): SolicitudModel = SolicitudModel(
        id = id,
        clienteId = clienteId ?: cliente?.id ?: 0,
        titulo = titulo,
        contenido = contenido,
        fechaHora = fechaHora.toReadableDate(),
        imagenes = imagenes,
        comentarios = comentarios.map { it.toDomain() },
        privada = privada
    )
}

@Serializable
data class RespuestaDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("solicitudId") val solicitudId: Int? = null,
    @SerialName("solicitud") val solicitud: EntityRefDto? = null,
    @SerialName("empresaId") val empresaId: Int? = null,
    @SerialName("empresa") val empresa: EntityRefDto? = null,
    @SerialName("clienteId") val clienteId: Int? = null,
    @SerialName("cliente") val cliente: EntityRefDto? = null,
    @SerialName("estado") val estado: String = EstadoRespuesta.EN_ESPERA.name,
    @SerialName("contenido") val contenido: String = "",
    @SerialName("comentarios") val comentarios: List<ComentarioRespuestaDto> = emptyList(),
    @SerialName("fechaHora") val fechaHora: String = ""
) {
    fun toDomain(): RespuestaModel = RespuestaModel(
        id = id,
        solicitudId = solicitudId ?: solicitud?.id ?: 0,
        empresaId = empresaId ?: empresa?.id ?: 0,
        clienteId = clienteId ?: cliente?.id ?: 0,
        estado = runCatching { EstadoRespuesta.valueOf(estado) }.getOrDefault(EstadoRespuesta.EN_ESPERA),
        contenido = contenido,
        comentarios = comentarios.map { it.toDomain() },
        fechaHora = fechaHora.toReadableDate()
    )
}

@Serializable
data class ComentarioSolicitudDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("solicitudId") val solicitudId: Int? = null,
    @SerialName("solicitud") val solicitud: EntityRefDto? = null,
    @SerialName("autor") val autor: String = "",
    @SerialName("contenido") val contenido: String = "",
    @SerialName("fechaHora") val fechaHora: String = "",
    @SerialName("respuestas") val respuestas: List<ComentarioSolicitudDto> = emptyList()
) {
    fun toDomain(): ComentarioSolicitudModel = ComentarioSolicitudModel(
        id = id,
        solicitudId = solicitudId ?: solicitud?.id ?: 0,
        autor = autor,
        contenido = contenido,
        fechaHora = fechaHora.toReadableDate(),
        respuestas = respuestas.map { it.toDomain() }
    )
}

@Serializable
data class ComentarioRespuestaDto(
    @SerialName("id") val id: Int = 0,
    @SerialName("respuestaId") val respuestaId: Int? = null,
    @SerialName("respuesta") val respuesta: EntityRefDto? = null,
    @SerialName("autor") val autor: String = "",
    @SerialName("contenido") val contenido: String = "",
    @SerialName("fechaHora") val fechaHora: String = "",
    @SerialName("respuestas") val respuestas: List<ComentarioRespuestaDto> = emptyList()
) {
    fun toDomain(): ComentarioRespuestaModel = ComentarioRespuestaModel(
        id = id,
        respuestaId = respuestaId ?: respuesta?.id ?: 0,
        autor = autor,
        contenido = contenido,
        fechaHora = fechaHora.toReadableDate(),
        respuestas = respuestas.map { it.toDomain() }
    )
}

private fun String.toReadableDate(): String {
    return replace("T", " ").substringBefore(".")
}
