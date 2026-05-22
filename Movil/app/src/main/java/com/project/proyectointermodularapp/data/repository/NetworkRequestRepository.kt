package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.data.network.RequestructureApiService
import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

class NetworkRequestRepository(
    private val apiService: RequestructureApiService
) : RequestRepository {
    override suspend fun getClientes(): List<ClienteModel> {
        return apiService.getClientes().map { it.toDomain() }
    }

    override suspend fun getEmpresas(): List<EmpresaModel> {
        return apiService.getEmpresas().map { it.toDomain() }
    }

    override suspend fun getSolicitudes(): List<SolicitudModel> {
        return apiService.getSolicitudes().map { it.toDomain() }
    }

    override suspend fun getRespuestas(): List<RespuestaModel> {
        return apiService.getRespuestas().map { it.toDomain() }
    }
}
