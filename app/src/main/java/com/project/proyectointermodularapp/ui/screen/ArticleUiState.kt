package com.project.proyectointermodularapp.ui.screen

import com.project.proyectointermodularapp.domain.model.ArticleModel

data class ArticleUiState(
    val isLoading: Boolean = false,
    val articles: List<ArticleModel> = emptyList(),
    val error: String? = null
)
