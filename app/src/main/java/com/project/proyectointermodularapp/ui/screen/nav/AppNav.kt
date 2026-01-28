package com.project.proyectointermodularapp.ui.screen.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.proyectointermodularapp.ui.screen.HomeScreen
import com.project.proyectointermodularapp.ui.screen.ArticlesScreen
import com.project.proyectointermodularapp.ui.screen.LoginScreen
import com.project.proyectointermodularapp.ui.screen.RegisterScreen

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {

        composable(Route.Login.route) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Route.Register.route) {
            RegisterScreen()
        }

        composable(Route.Home.route) {
            HomeScreen()
        }

        composable(Route.Articles.route) {
            ArticlesScreen()
        }
    }
}
