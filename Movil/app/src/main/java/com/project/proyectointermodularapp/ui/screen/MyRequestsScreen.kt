package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.proyectointermodularapp.domain.model.ViewerSession
import com.project.proyectointermodularapp.ui.components.RequestCard
import com.project.proyectointermodularapp.ui.theme.AlmosWhite
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun MyRequestsScreen(
    onRequestClick: (Int) -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: RequestViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    if (uiState.viewerSession == ViewerSession.Invitado) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Debes iniciar sesion para ver tus solicitudes.",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Grey
                )
                Button(
                    onClick = onNavigateToLogin,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Red,
                        contentColor = AlmosWhite
                    )
                ) {
                    Text("Iniciar sesion")
                }
            }
        }
        return
    }

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var isPrivate by remember { mutableStateOf(false) }
    var formMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Tus solicitudes",
                style = MaterialTheme.typography.headlineLarge,
                color = Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier.padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Nueva solicitud",
                        style = MaterialTheme.typography.titleMedium,
                        color = Red
                    )

                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Titulo") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = content,
                        onValueChange = { content = it },
                        label = { Text("Contenido") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )

                    OutlinedTextField(
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        label = { Text("URL de imagen (opcional)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Solicitud privada")
                        Switch(
                            checked = isPrivate,
                            onCheckedChange = { isPrivate = it }
                        )
                    }

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                val created = viewModel.createRequest(
                                    title = title,
                                    content = content,
                                    isPrivate = isPrivate,
                                    imageUrl = imageUrl
                                )

                                formMessage = if (created) {
                                    title = ""
                                    content = ""
                                    imageUrl = ""
                                    isPrivate = false
                                    "Solicitud creada correctamente."
                                } else {
                                    "No se pudo crear la solicitud. Intenta de nuevo."
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !uiState.isLoading
                    ) {
                        Text(if (uiState.isLoading) "Publicando..." else "Publicar solicitud")
                    }

                    formMessage?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodySmall,
                            color = if (it.contains("correctamente")) Red else MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Mis publicaciones",
                style = MaterialTheme.typography.titleLarge,
                color = Red
            )
        }

        if (uiState.myRequests.isEmpty()) {
            item {
                Text(
                    text = "Aun no has creado solicitudes.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Grey,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }
        } else {
            items(
                items = uiState.myRequests,
                key = { it.id }
            ) { request ->
                RequestCard(
                    title = request.title,
                    content = request.content,
                    author = request.author,
                    date = request.date,
                    imageUrl = request.imageUrl,
                    onClick = { onRequestClick(request.id) }
                )
            }
        }
    }
}
