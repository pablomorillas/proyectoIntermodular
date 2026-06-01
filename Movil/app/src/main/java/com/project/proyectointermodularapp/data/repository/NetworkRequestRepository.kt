package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.data.network.CreateClienteRequestDto
import com.project.proyectointermodularapp.data.network.LoginRequestDto
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

    override suspend fun login(email: String, password: String): ClienteModel? {
        return try {
            apiService.login(LoginRequestDto(email.trim().lowercase(), password)).toDomain()
        } catch (e: Exception) {
            android.util.Log.e("NetworkRepo", "Login failed: ${e.message}")
            null
        }
    }

    override suspend fun register(username: String, email: String, password: String, direccion: String): ClienteModel? {
        return try {
            apiService.createCliente(
                CreateClienteRequestDto(
                    username = username.trim(),
                    email = email.trim().lowercase(),
                    password = password,
                    direccion = direccion.trim().ifEmpty { "Sin especificar" }
                )
            ).toDomain()
        } catch (e: Exception) {
            android.util.Log.e("NetworkRepo", "Register failed: ${e.message}")
            null
        }
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
