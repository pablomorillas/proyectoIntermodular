package com.project.proyectointermodularapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.project.proyectointermodularapp.domain.model.CommentModel

@Composable
fun CommentItem(comment: CommentModel, indent: Int = 0) {
    Column(
        modifier = Modifier.padding(start = (indent * 16).dp)
    ) {
        Text(text = comment.author, style = MaterialTheme.typography.labelMedium)
        Text(text = comment.message)

        comment.replies.forEach {
            CommentItem(it, indent + 1)
        }
    }
}
