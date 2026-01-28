package com.project.proyectointermodularapp.data.repository

import com.project.proyectointermodularapp.domain.model.ArticleModel
import com.project.proyectointermodularapp.domain.model.CommentModel

class FakeArticleRepository : ArticleRepository {

    override suspend fun getArticles(): List<ArticleModel> {

        return listOf(
            ArticleModel(
                id = 1,
                title = "Jetpack Compose desde cero",
                content = "Aprende a crear interfaces modernas y declarativas con Jetpack Compose.",
                author = "Lucía Martínez",
                date = "2024-01-12",
                imageUrl = "https://picsum.photos/400/200?1",
                comments = listOf(
                    CommentModel(
                        id = 1,
                        author = "Carlos",
                        message = "Muy buen artículo 👏",
                        date = "2024-01-13",
                        replies = listOf(
                            CommentModel(
                                id = 2,
                                author = "Lucía Martínez",
                                message = "¡Gracias!",
                                date = "2024-01-13"
                            )
                        )
                    )
                ),
                responses = emptyList()
            ),
            ArticleModel(
                id = 2,
                title = "Arquitectura MVVM en Android",
                content = "Organiza tu proyecto Android usando ViewModel, UiState y Repository.",
                author = "Carlos Gómez",
                date = "2024-01-15",
                imageUrl = "https://picsum.photos/400/200?2",
            ),
            ArticleModel(
                id = 3,
                title = "Buenas prácticas en Compose",
                content = "Consejos y patrones para escribir código Compose limpio y escalable.",
                author = "Ana López",
                date = "2024-01-18",
                imageUrl = "https://picsum.photos/400/200?3"
            ),
            ArticleModel(
                id = 4,
                title = "StateFlow vs LiveData",
                content = "Comparativa práctica para manejar estado en aplicaciones modernas.",
                author = "Miguel Torres",
                date = "2024-01-20",
                imageUrl = "https://picsum.photos/400/200?4"
            ),
            ArticleModel(
                id = 5,
                title = "Errores comunes en Android",
                content = "Los fallos más habituales al desarrollar apps Android y cómo evitarlos.",
                author = "Laura Sánchez",
                date = "2024-01-22",
                imageUrl = "https://picsum.photos/400/200?5"
            )
        )
    }
}
