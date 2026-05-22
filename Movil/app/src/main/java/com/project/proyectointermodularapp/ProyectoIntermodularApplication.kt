package com.project.proyectointermodularapp

import android.app.Application
import com.project.proyectointermodularapp.data.AppContainer
import com.project.proyectointermodularapp.data.DefaultAppContainer

class ProyectoIntermodularApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}
