package com.project.proyectointermodularapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.proyectointermodularapp.data.repository.RequestRepository
import com.project.proyectointermodularapp.data.repository.FakeRequestRepository
import com.project.proyectointermodularapp.domain.model.RequestModel
import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.CommentModel
import com.project.proyectointermodularapp.domain.model.ComentarioSolicitudModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel
import com.project.proyectointermodularapp.domain.model.ViewerSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RequestViewModel(
    private val repository: RequestRepository = FakeRequestRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(RequestUiState(isLoading = true))
    val uiState: StateFlow<RequestUiState> = _uiState.asStateFlow()

    private var clientesCache: List<ClienteModel> = emptyList()
    private var empresasCache: List<EmpresaModel> = emptyList()
    private var solicitudesCache: List<SolicitudModel> = emptyList()
    private var respuestasCache: List<RespuestaModel> = emptyList()

    init {
        loadDomainData()
    }

    fun setViewerSession(viewerSession: ViewerSession) {
        _uiState.value = _uiState.value.copy(viewerSession = viewerSession)
        rebuildUiStateFromCache()
    }

    private fun loadDomainData() {
        viewModelScope.launch {
            try {
                val clientes = repository.getClientes()
                val empresas = repository.getEmpresas()
                val solicitudes = repository.getSolicitudes()
                val respuestas = repository.getRespuestas()

                clientesCache = clientes
                empresasCache = empresas
                solicitudesCache = solicitudes
                respuestasCache = respuestas

                rebuildUiStateFromCache()
            } catch (_: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudieron cargar los datos"
                )
            }
        }
    }

    private fun rebuildUiStateFromCache() {
        if (clientesCache.isEmpty() && empresasCache.isEmpty() && solicitudesCache.isEmpty()) {
            return
        }

        val viewer = _uiState.value.viewerSession

        val solicitudesVisibles = filterSolicitudesVisibles(
            solicitudes = solicitudesCache,
            viewerSession = viewer
        )

        val respuestasVisibles = filterRespuestasVisibles(
            respuestas = respuestasCache,
            viewerSession = viewer
        )

        val requestItems = solicitudesVisibles.map { solicitud ->
            solicitud.toRequestModel(
                clientes = clientesCache,
                empresas = empresasCache,
                respuestasVisibles = respuestasVisibles
            )
        }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            requests = requestItems,
            clientes = clientesCache,
            empresas = empresasCache,
            solicitudesVisibles = solicitudesVisibles,
            respuestasVisibles = respuestasVisibles,
            error = null
        )
    }

    private fun filterSolicitudesVisibles(
        solicitudes: List<SolicitudModel>,
        viewerSession: ViewerSession
    ): List<SolicitudModel> {
        return when (viewerSession) {
            is ViewerSession.Empresa -> solicitudes
            is ViewerSession.Cliente -> solicitudes.filter { solicitud ->
                !solicitud.privada || solicitud.clienteId == viewerSession.id
            }
        }
    }

    private fun filterRespuestasVisibles(
        respuestas: List<RespuestaModel>,
        viewerSession: ViewerSession
    ): List<RespuestaModel> {
        return when (viewerSession) {
            is ViewerSession.Empresa -> respuestas.filter { it.empresaId == viewerSession.id }
            is ViewerSession.Cliente -> respuestas.filter { it.clienteId == viewerSession.id }
        }
    }

    private fun SolicitudModel.toRequestModel(
        clientes: List<ClienteModel>,
        empresas: List<EmpresaModel>,
        respuestasVisibles: List<RespuestaModel>
    ): RequestModel {
        val authorName = clientes
            .firstOrNull { it.id == clienteId }
            ?.username
            ?: "usuario-$clienteId"

        val responseLines = respuestasVisibles
            .filter { it.solicitudId == id }
            .map { response ->
                val companyName = empresas
                    .firstOrNull { it.id == response.empresaId }
                    ?.nombre
                    ?: "empresa-${response.empresaId}"
                "$companyName: ${response.contenido} [${response.estado}]"
            }

        return RequestModel(
            id = id,
            title = titulo,
            content = contenido,
            author = authorName,
            date = fechaHora,
            imageUrl = imagenes.firstOrNull().orEmpty(),
            responses = responseLines,
            comments = comentarios.map { it.toLegacyComment() }
        )
    }

    private fun ComentarioSolicitudModel.toLegacyComment(): CommentModel {
        return CommentModel(
            id = id,
            author = autor,
            message = contenido,
            date = fechaHora,
            replies = respuestas.map { it.toLegacyComment() }
        )
    }
}

