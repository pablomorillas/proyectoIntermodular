package com.project.proyectointermodularapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.proyectointermodularapp.domain.model.ViewerSession
import com.project.proyectointermodularapp.ui.components.RequestDetail
import com.project.proyectointermodularapp.ui.theme.AlmosWhite
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun RequestDetailScreen(
    requestId: Int,
    onNavigateToLogin: () -> Unit,
    viewModel: RequestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var commentText by rememberSaveable(requestId) { mutableStateOf("") }
    var showLoginDialog by remember { mutableStateOf(false) }

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Red)
            }
        }

        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.error ?: "No se pudo cargar la solicitud.",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            val request = uiState.requests.find { it.id == requestId }
            if (request == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Solicitud no encontrada.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                RequestDetail(
                    request = request,
                    canComment = uiState.viewerSession != ViewerSession.Invitado,
                    newCommentText = commentText,
                    onNewCommentTextChange = { commentText = it },
                    onPublishCommentClick = {
                        when (viewModel.addCommentToRequest(requestId, commentText)) {
                            AddRequestCommentResult.SUCCESS -> {
                                commentText = ""
                                Toast.makeText(
                                    context,
                                    "Comentario publicado correctamente.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            AddRequestCommentResult.EMPTY_CONTENT -> {
                                Toast.makeText(
                                    context,
                                    "Escribe un comentario antes de publicar.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            AddRequestCommentResult.REQUIRE_LOGIN -> {
                                showLoginDialog = true
                            }

                            AddRequestCommentResult.REQUEST_NOT_FOUND -> {
                                Toast.makeText(
                                    context,
                                    "No se encontro la solicitud.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    if (showLoginDialog) {
        AlertDialog(
            onDismissRequest = { showLoginDialog = false },
            title = { Text("Inicia sesion") },
            text = {
                Text("Debes iniciar sesion para comentar en una solicitud.")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLoginDialog = false
                        onNavigateToLogin()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red,
                        contentColor = AlmosWhite
                    )
                ) {
                    Text("Iniciar sesion")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showLoginDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grey,
                        contentColor = AlmosWhite
                    )
                ) {
                    Text("Mas tarde")
                }
            }
        )
    }
}
