package com.project.proyectointermodularapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.project.proyectointermodularapp.domain.model.CommentModel

@Composable
fun CommentItem(
    comment: CommentModel,
    indent: Int = 0,
    canReply: Boolean = false,
    replyingToCommentId: Int? = null,
    replyText: String = "",
    onReplyTextChange: (String) -> Unit = {},
    onStartReply: (Int) -> Unit = {},
    onCancelReply: () -> Unit = {},
    onPublishReply: (Int) -> Unit = {},
    currentUserName: String = "",
    onDeleteComment: (Int) -> Unit = {}
) {
    Column(
        modifier = Modifier.padding(start = (indent * 16).dp)
    ) {
        Text(text = comment.author, style = MaterialTheme.typography.labelMedium)
        Text(
            text = comment.date,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(text = comment.message)
        Spacer(modifier = Modifier.height(4.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            if (canReply && replyingToCommentId != comment.id) {
                Text(
                    text = "Responder",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onStartReply(comment.id) }
                )
            }
            if (currentUserName.isNotBlank() && comment.author == currentUserName) {
                Text(
                    text = "Eliminar",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable { onDeleteComment(comment.id) }
                )
            }
        }

        if (replyingToCommentId == comment.id) {
            OutlinedTextField(
                value = replyText,
                onValueChange = onReplyTextChange,
                label = { Text("Escribe una respuesta") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { onPublishReply(comment.id) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Publicar")
                }
                OutlinedButton(
                    onClick = onCancelReply,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        comment.replies.forEach {
            CommentItem(
                comment = it,
                indent = indent + 1,
                canReply = canReply,
                replyingToCommentId = replyingToCommentId,
                replyText = replyText,
                onReplyTextChange = onReplyTextChange,
                onStartReply = onStartReply,
                onCancelReply = onCancelReply,
                onPublishReply = onPublishReply,
                currentUserName = currentUserName,
                onDeleteComment = onDeleteComment
            )
        }
    }
}
