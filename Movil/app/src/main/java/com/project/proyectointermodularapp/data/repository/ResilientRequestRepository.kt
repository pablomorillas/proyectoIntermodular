package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

class ResilientRequestRepository(
    private val repository: RequestRepository
) : RequestRepository {

    override suspend fun getClientes(): List<ClienteModel> = repository.getClientes()
    override suspend fun getEmpresas(): List<EmpresaModel> = repository.getEmpresas()
    override suspend fun getSolicitudes(): List<SolicitudModel> = repository.getSolicitudes()
    override suspend fun createSolicitud(clienteId: Int, titulo: String, contenido: String, imagenes: List<String>, privada: Boolean): SolicitudModel? =
        repository.createSolicitud(clienteId, titulo, contenido, imagenes, privada)
    override suspend fun login(email: String, password: String): ClienteModel? = repository.login(email, password)
    override suspend fun register(username: String, email: String, password: String, direccion: String): ClienteModel? =
        repository.register(username, email, password, direccion)
    override suspend fun getRespuestas(): List<RespuestaModel> = repository.getRespuestas()
}
