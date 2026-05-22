package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

class ResilientRequestRepository(
    private val remoteRepository: RequestRepository,
    private val fallbackRepository: RequestRepository
) : RequestRepository {
    private var remoteUnavailable = false

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
        if (remoteUnavailable) {
            return fallbackCall()
        }

        return runCatching { remoteCall() }
            .getOrElse {
                remoteUnavailable = true
                fallbackCall()
            }
    }
}
