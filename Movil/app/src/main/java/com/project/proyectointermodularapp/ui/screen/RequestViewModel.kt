package com.project.proyectointermodularapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.project.proyectointermodularapp.ProyectoIntermodularApplication
import com.project.proyectointermodularapp.data.repository.FakeRequestRepository
import com.project.proyectointermodularapp.data.repository.RequestRepository
import com.project.proyectointermodularapp.domain.model.RequestModel
import com.project.proyectointermodularapp.domain.model.ClienteModel
import com.project.proyectointermodularapp.domain.model.CommentModel
import com.project.proyectointermodularapp.domain.model.ComentarioSolicitudModel
import com.project.proyectointermodularapp.domain.model.EmpresaModel
import com.project.proyectointermodularapp.domain.model.RespuestaModel
import com.project.proyectointermodularapp.domain.model.SolicitudModel
import com.project.proyectointermodularapp.domain.model.ViewerSession
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class AddRequestCommentResult {
    SUCCESS,
    REQUIRE_LOGIN,
    EMPTY_CONTENT,
    REQUEST_NOT_FOUND
}

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

    suspend fun login(email: String, password: String): Boolean {
        return try {
            val client = repository.login(email, password) ?: return false
            clientesCache = clientesCache.filter { it.id != client.id } + client
            setViewerSession(ViewerSession.Cliente(id = client.id))
            true
        } catch (_: Exception) {
            false
        }
    }

    suspend fun register(username: String, email: String, password: String, direccion: String = "Sin especificar"): ClienteModel? {
        return try {
            val newClient = repository.register(username, email, password, direccion) ?: return null
            clientesCache = clientesCache + newClient
            rebuildUiStateFromCache()
            newClient
        } catch (_: Exception) {
            null
        }
    }

    fun createRequest(
        title: String,
        content: String,
        isPrivate: Boolean,
        imageUrl: String? = null
    ): Boolean {
        val currentClientId = getCurrentClientId() ?: return false
        val normalizedTitle = title.trim()
        val normalizedContent = content.trim()

        if (normalizedTitle.isEmpty() || normalizedContent.isEmpty()) {
            return false
        }

        val newRequestId = (solicitudesCache.maxOfOrNull { it.id } ?: 0) + 1
        val fallbackImage = "https://picsum.photos/seed/request-$newRequestId/800/450"
        val normalizedImage = imageUrl?.trim().orEmpty()
        val finalImage = if (normalizedImage.isEmpty()) fallbackImage else normalizedImage
        val now = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())

        val newRequest = SolicitudModel(
            id = newRequestId,
            clienteId = currentClientId,
            titulo = normalizedTitle,
            contenido = normalizedContent,
            fechaHora = now,
            imagenes = listOf(finalImage),
            privada = isPrivate,
            comentarios = emptyList()
        )

        solicitudesCache = solicitudesCache + newRequest
        clientesCache = clientesCache.map { client ->
            if (client.id == currentClientId) {
                client.copy(solicitudesIds = client.solicitudesIds + newRequestId)
            } else {
                client
            }
        }

        rebuildUiStateFromCache()
        return true
    }

    fun addCommentToRequest(
        requestId: Int,
        commentText: String
    ): AddRequestCommentResult {
        val normalizedComment = commentText.trim()
        if (normalizedComment.isEmpty()) {
            return AddRequestCommentResult.EMPTY_CONTENT
        }

        val viewerSession = _uiState.value.viewerSession
        if (viewerSession == ViewerSession.Invitado) {
            return AddRequestCommentResult.REQUIRE_LOGIN
        }

        val requestExists = solicitudesCache.any { it.id == requestId }
        if (!requestExists) {
            return AddRequestCommentResult.REQUEST_NOT_FOUND
        }

        val author = when (viewerSession) {
            is ViewerSession.Cliente -> clientesCache
                .firstOrNull { it.id == viewerSession.id }
                ?.username
                ?: "cliente-${viewerSession.id}"

            is ViewerSession.Empresa -> empresasCache
                .firstOrNull { it.id == viewerSession.id }
                ?.nombre
                ?: "empresa-${viewerSession.id}"

            ViewerSession.Invitado -> return AddRequestCommentResult.REQUIRE_LOGIN
        }

        val currentMaxCommentId = solicitudesCache
            .flatMap { solicitud -> flattenSolicitudComments(solicitud.comentarios) }
            .maxOfOrNull { comment -> comment.id }
            ?: 0

        val newComment = ComentarioSolicitudModel(
            id = currentMaxCommentId + 1,
            solicitudId = requestId,
            autor = author,
            contenido = normalizedComment,
            fechaHora = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
            respuestas = emptyList()
        )

        solicitudesCache = solicitudesCache.map { solicitud ->
            if (solicitud.id == requestId) {
                solicitud.copy(comentarios = solicitud.comentarios + newComment)
            } else {
                solicitud
            }
        }

        rebuildUiStateFromCache()
        return AddRequestCommentResult.SUCCESS
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

        val requestItems = solicitudesVisibles
            .sortedByDescending { it.id }
            .map { solicitud ->
                solicitud.toRequestModel(
                    clientes = clientesCache,
                    empresas = empresasCache,
                    respuestasVisibles = respuestasVisibles
                )
            }

        val myRequestItems = when (val viewer = _uiState.value.viewerSession) {
            ViewerSession.Invitado -> emptyList()
            is ViewerSession.Cliente -> solicitudesCache
                .filter { it.clienteId == viewer.id }
                .sortedByDescending { it.id }
                .map { solicitud ->
                    solicitud.toRequestModel(
                        clientes = clientesCache,
                        empresas = empresasCache,
                        respuestasVisibles = respuestasVisibles
                    )
                }
            is ViewerSession.Empresa -> emptyList()
        }

        _uiState.value = _uiState.value.copy(
            isLoading = false,
            requests = requestItems,
            myRequests = myRequestItems,
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
            ViewerSession.Invitado -> solicitudes.filter { !it.privada }
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
            ViewerSession.Invitado -> emptyList()
            is ViewerSession.Empresa -> respuestas.filter { it.empresaId == viewerSession.id }
            is ViewerSession.Cliente -> respuestas.filter { it.clienteId == viewerSession.id }
        }
    }

    private fun getCurrentClientId(): Int? {
        return when (val viewer = _uiState.value.viewerSession) {
            ViewerSession.Invitado -> null
            is ViewerSession.Cliente -> viewer.id
            is ViewerSession.Empresa -> null
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

    private fun flattenSolicitudComments(
        comments: List<ComentarioSolicitudModel>
    ): List<ComentarioSolicitudModel> {
        return comments.flatMap { comment ->
            listOf(comment) + flattenSolicitudComments(comment.respuestas)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as ProyectoIntermodularApplication
                RequestViewModel(
                    repository = application.container.requestRepository
                )
            }
        }
    }
}
