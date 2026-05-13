package com.project.proyectointermodularapp.domain.model

data class CommentModel(
    val id: Int,
    val author: String,
    val message: String,
    val date: String,
    val replies: List<CommentModel> = emptyList()
)
