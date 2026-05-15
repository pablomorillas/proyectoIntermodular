package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.proyectointermodularapp.ui.components.RequestDetail
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun RequestDetailScreen(
    requestId: Int,
    viewModel: RequestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
                Box(modifier = Modifier.fillMaxSize()) {
                    RequestDetail(
                        request = request,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
