package com.project.proyectointermodularapp.ui.screen

import com.project.proyectointermodularapp.domain.model.RequestModel
import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel
import com.project.proyectointermodularapp.domain.model.ViewerSession

data class RequestUiState(
    val isLoading: Boolean = false,
    val requests: List<RequestModel> = emptyList(),
    val myRequests: List<RequestModel> = emptyList(),
    val viewerSession: ViewerSession = ViewerSession.Invitado,
    val clientes: List<ClienteModel> = emptyList(),
    val empresas: List<EmpresaModel> = emptyList(),
    val solicitudesVisibles: List<SolicitudModel> = emptyList(),
    val respuestasVisibles: List<RespuestaModel> = emptyList(),
    val error: String? = null
)

