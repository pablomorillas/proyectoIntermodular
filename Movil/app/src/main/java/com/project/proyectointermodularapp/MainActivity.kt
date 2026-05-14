package com.project.proyectointermodularapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.project.proyectointermodularapp.ui.screen.MainScreen
import com.project.proyectointermodularapp.ui.theme.ProyectoIntermodularAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoIntermodularAppTheme {
                MainScreen()
            }
        }
    }
}
