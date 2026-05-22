package com.project.proyectointermodularapp.data.network

import retrofit2.http.GET

interface RequestructureApiService {
    @GET("clientes")
    suspend fun getClientes(): List<ClienteDto>

    @GET("empresas")
    suspend fun getEmpresas(): List<EmpresaDto>

    @GET("solicitudes")
    suspend fun getSolicitudes(): List<SolicitudDto>

    @GET("respuestas")
    suspend fun getRespuestas(): List<RespuestaDto>
}
