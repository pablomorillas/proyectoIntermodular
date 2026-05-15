package com.project.proyectointermodularapp.domain.model

data class RequestModel(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: String,
    val imageUrl: String,
    val responses: List<String> = emptyList(),
    val comments: List<CommentModel> = emptyList()
)

