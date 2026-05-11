package com.project.proyectointermodularapp.ui.screen

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.project.proyectointermodularapp.R
import com.project.proyectointermodularapp.domain.model.ArticleModel
import com.project.proyectointermodularapp.ui.theme.AlmosWhite
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun HomeScreen(
    onArticleClick: (Int) -> Unit = {},
    viewModel: ArticleViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var randomSeed by remember { mutableIntStateOf(0) }

    val alertArticle = remember(uiState.articles, randomSeed) {
        uiState.articles
            .filter { it.responses.isNotEmpty() }
            .shuffled()
            .firstOrNull()
    }

    val randomArticles = remember(uiState.articles, randomSeed) {
        uiState.articles
            .shuffled()
            .take(4)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AlmosWhite)
    ) {
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
                        text = uiState.error ?: "No se pudieron cargar los articulos.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Inicio",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Red,
                                fontWeight = FontWeight.Bold
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                TextButton(onClick = { randomSeed++ }) {
                                    Text("Actualizar")
                                }

                                ProfileAvatarPlaceholder()
                            }
                        }
                    }

                    if (alertArticle != null) {
                        item {
                            AlertResponseCard(
                                article = alertArticle,
                                onClick = { onArticleClick(alertArticle.id) }
                            )
                        }
                    }

                    item {
                        Text(
                            text = "Recomendados",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A1A1A)
                        )
                    }

                    if (randomArticles.isEmpty()) {
                        item {
                            Text(
                                text = "Todavia no hay publicaciones disponibles.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = Grey
                            )
                        }
                    } else {
                        items(
                            items = randomArticles,
                            key = { it.id }
                        ) { article ->
                            HomeArticleCard(
                                article = article,
                                onClick = { onArticleClick(article.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileAvatarPlaceholder() {
    Box(
        modifier = Modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(Color(0xFFE9E9E9))
            .border(1.dp, Color(0xFFD4D4D4), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.article_person),
            contentDescription = "Foto de perfil",
            modifier = Modifier.size(22.dp)
        )
    }
}

@Composable
private fun AlertResponseCard(
    article: ArticleModel,
    onClick: () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "alert-motion")
    val offsetX by infiniteTransition.animateFloat(
        initialValue = -4f,
        targetValue = 4f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alert-offset"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = offsetX.dp)
            .clickable { onClick() }
            .border(1.dp, Red, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEFEF))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = "Imagen de la peticion",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = "ALERTA DE EMPRESA",
                style = MaterialTheme.typography.labelLarge,
                color = Red,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Tienes una respuesta nueva a una peticion.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF3D3D3D)
            )

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = article.responses.firstOrNull() ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = Grey,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun HomeArticleCard(
    article: ArticleModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = "Imagen de la peticion",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = article.content,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF3D3D3D),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = "${article.author} · ${article.date}",
                style = MaterialTheme.typography.labelSmall,
                color = Grey
            )
        }
    }
}
