package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel

interface RequestRepository {
    suspend fun getClientes(): List<ClienteModel>
    suspend fun login(email: String, password: String): ClienteModel?
    suspend fun register(username: String, email: String, password: String, direccion: String): ClienteModel?
    suspend fun getEmpresas(): List<EmpresaModel>
    suspend fun getSolicitudes(): List<SolicitudModel>
    suspend fun getRespuestas(): List<RespuestaModel>
}

