package com.project.proyectointermodularapp.ui.screen.nav

sealed class Route(val route: String, val index: Int? = null) {

    object Login : Route("login")
    object Register : Route("register")

    object Home : Route("home", 0)
    object Articles : Route("articles", 1)
}
