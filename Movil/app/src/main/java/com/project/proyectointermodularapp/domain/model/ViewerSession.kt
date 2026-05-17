package com.project.proyectointermodularapp.domain.model

sealed interface ViewerSession {
    data object Invitado : ViewerSession
    data class Cliente(val id: Int) : ViewerSession
    data class Empresa(val id: Int) : ViewerSession
}
