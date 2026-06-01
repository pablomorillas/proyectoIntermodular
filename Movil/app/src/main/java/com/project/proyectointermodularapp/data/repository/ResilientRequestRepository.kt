package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

class ResilientRequestRepository(
    private val remoteRepository: RequestRepository,
    private val fallbackRepository: RequestRepository
) : RequestRepository {

    override suspend fun getClientes(): List<ClienteModel> {
        return getFromRemoteOrFallback(
            remoteCall = { remoteRepository.getClientes() },
            fallbackCall = { fallbackRepository.getClientes() }
        )
    }

    override suspend fun getEmpresas(): List<EmpresaModel> {
        return getFromRemoteOrFallback(
            remoteCall = { remoteRepository.getEmpresas() },
            fallbackCall = { fallbackRepository.getEmpresas() }
        )
    }

    override suspend fun getSolicitudes(): List<SolicitudModel> {
        return getFromRemoteOrFallback(
            remoteCall = { remoteRepository.getSolicitudes() },
            fallbackCall = { fallbackRepository.getSolicitudes() }
        )
    }

    override suspend fun login(email: String, password: String): ClienteModel? {
        return runCatching { remoteRepository.login(email, password) }
            .getOrElse { fallbackRepository.login(email, password) }
    }

    override suspend fun register(username: String, email: String, password: String, direccion: String): ClienteModel? {
        return runCatching { remoteRepository.register(username, email, password, direccion) }
            .getOrElse { fallbackRepository.register(username, email, password, direccion) }
    }

    override suspend fun getRespuestas(): List<RespuestaModel> {
        return getFromRemoteOrFallback(
            remoteCall = { remoteRepository.getRespuestas() },
            fallbackCall = { fallbackRepository.getRespuestas() }
        )
    }

    private suspend fun <T> getFromRemoteOrFallback(
        remoteCall: suspend () -> T,
        fallbackCall: suspend () -> T
    ): T {
        return runCatching { remoteCall() }
            .getOrElse { fallbackCall() }
    }
}
