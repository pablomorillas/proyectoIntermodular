package com.project.proyectointermodularapp.ui.screen.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.proyectointermodularapp.ui.screen.ArticleDetailScreen
import com.project.proyectointermodularapp.ui.screen.HomeScreen
import com.project.proyectointermodularapp.ui.screen.ArticlesScreen
import com.project.proyectointermodularapp.ui.screen.InboxScreen
import com.project.proyectointermodularapp.ui.screen.LoginScreen
import com.project.proyectointermodularapp.ui.screen.MyRequestsScreen
import com.project.proyectointermodularapp.ui.screen.RegisterScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier
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
                },
                onRegisterClick = {
                    navController.navigate(Route.Register.route)
                }
            )
        }

        composable(Route.Register.route) {
            RegisterScreen(
                onLoginClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Route.Login.route) {
                            launchSingleTop = true
                        }
                    }
                }
            )
        }

        composable(Route.Home.route) {
            HomeScreen()
        }

        composable(Route.Articles.route) {
            ArticlesScreen()
        }
    }
}
