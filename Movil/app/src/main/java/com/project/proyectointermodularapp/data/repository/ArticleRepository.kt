package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ArticleModel

interface ArticleRepository {
    suspend fun getArticles(): List<ArticleModel>
}
