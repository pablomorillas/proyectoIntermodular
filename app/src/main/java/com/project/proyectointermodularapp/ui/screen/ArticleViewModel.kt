package com.project.proyectointermodularapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.proyectointermodularapp.data.repository.ArticleRepository
import com.project.proyectointermodularapp.data.repository.FakeArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: ArticleRepository = FakeArticleRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleUiState(isLoading = true))
    val uiState: StateFlow<ArticleUiState> = _uiState.asStateFlow()

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                val articles = repository.getArticles()
                _uiState.value = ArticleUiState(articles = articles)
            } catch (e: Exception) {
                _uiState.value = ArticleUiState(
                    error = "No se pudieron cargar los artículos"
                )
            }
        }
    }
}
