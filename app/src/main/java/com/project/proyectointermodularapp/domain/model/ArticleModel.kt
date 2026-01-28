package com.project.proyectointermodularapp.domain.model

data class ArticleModel(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: String,
    val imageUrl: String
)
