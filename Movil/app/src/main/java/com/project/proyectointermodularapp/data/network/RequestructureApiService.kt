package com.project.proyectointermodularapp.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RequestructureApiService {
    @GET("clientes")
    suspend fun getClientes(): List<ClienteDto>

    @POST("clientes/login")
    suspend fun login(@Body request: LoginRequestDto): ClienteDto

    @POST("clientes")
    suspend fun createCliente(@Body request: CreateClienteRequestDto): ClienteDto

    @GET("empresas")
    suspend fun getEmpresas(): List<EmpresaDto>

    @GET("solicitudes")
    suspend fun getSolicitudes(): List<SolicitudDto>

    @POST("solicitudes")
    suspend fun createSolicitud(@Body request: CreateSolicitudRequestDto): SolicitudDto

    @GET("respuestas")
    suspend fun getRespuestas(): List<RespuestaDto>
}
