package com.project.proyectointermodularapp.ui.screen.nav

sealed class Route(val route: String, val index: Int? = null) {

    object Login : Route("login")
    object Register : Route("register")

    object Home : Route("home", 0)
    object Articles : Route("articles", 1)
    object MyRequests : Route("my_requests", 2)
    object CompanyRequests : Route("company_requests", 3)
    object ArticleDetail : Route("article_detail/{articleId}") {
        fun createRoute(articleId: Int): String = "article_detail/$articleId"
    }
}
