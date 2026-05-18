package com.project.proyectointermodularapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.project.proyectointermodularapp.domain.model.RequestModel
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun RequestDetail(
    request: RequestModel,
    canComment: Boolean,
    newCommentText: String,
    onNewCommentTextChange: (String) -> Unit,
    onPublishCommentClick: () -> Unit,
    modifier: Modifier = Modifier,
    topContentPadding: Dp = 0.dp
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            top = 16.dp + topContentPadding,
            end = 16.dp,
            bottom = 24.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AsyncImage(
                model = request.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = request.title,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Por ${request.author} - ${request.date}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = request.content,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        item {
            Text(
                text = "Respuestas",
                style = MaterialTheme.typography.titleLarge,
                color = Red
            )
        }

        if (request.responses.isEmpty()) {
            item {
                Text(
                    text = "Aun no hay respuestas para esta solicitud.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey
                )
            }
        } else {
            request.responses.forEach { response ->
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface
                        )
                    ) {
                        Text(
                            text = response,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Comentarios",
                style = MaterialTheme.typography.titleLarge,
                color = Red
            )
        }

        if (request.comments.isEmpty()) {
            item {
                Text(
                    text = "Aun no hay comentarios.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Grey
                )
            }
        } else {
            item {
                request.comments.forEach { comment ->
                    CommentItem(comment = comment)
                }
            }
        }

        item {
            OutlinedTextField(
                value = newCommentText,
                onValueChange = onNewCommentTextChange,
                label = { Text("Escribe un comentario") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                enabled = canComment
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onPublishCommentClick,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (canComment) Red else Grey,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = if (canComment) {
                        "Publicar comentario"
                    } else {
                        "Inicia sesion para comentar"
                    }
                )
            }
        }
    }
}
