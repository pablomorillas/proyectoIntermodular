package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.proyectointermodularapp.ui.components.Article
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun ArticlesScreen(
    viewModel: ArticleViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text(
            text = "Articulos públicos",
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

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(uiState.articles) { article ->
                        Article(
                            title = article.title,
                            content = article.content,
                            author = article.author,
                            date = article.date,
                            imageUrl = article.imageUrl
                        )
                    }
                }
            }
        }
    }


}