package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

private data class CompanyRequestUiModel(
    val id: String,
    val companyName: String,
    val details: String,
    val requestTitle: String,
    val requestDate: String
)

@Composable
fun InboxScreen(
    modifier: Modifier = Modifier,
    viewModel: RequestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val companyRequests = uiState.requests.flatMap { request ->
        request.responses.mapIndexed { index, response ->
            val responseParts = response.split(":", limit = 2)
            val companyName = responseParts.firstOrNull()?.trim().orEmpty()
            val details = responseParts.getOrElse(1) { response }.trim()

            CompanyRequestUiModel(
                id = "${request.id}-$index",
                companyName = if (companyName.isBlank()) "Empresa" else companyName,
                details = details,
                requestTitle = request.title,
                requestDate = request.date
            )
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Solicitudes de empresa",
            style = MaterialTheme.typography.headlineLarge,
            color = Red,
            modifier = Modifier.padding(16.dp)
        )

        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = uiState.error ?: "Error desconocido",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            companyRequests.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Aun no tienes solicitudes de empresa.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = companyRequests,
                        key = { it.id }
                    ) { request ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = request.companyName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Red
                                )

                                Text(
                                    text = request.details,
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Text(
                                    text = "Solicitud sobre: ${request.requestTitle}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Grey
                                )

                                Text(
                                    text = request.requestDate,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Grey
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
