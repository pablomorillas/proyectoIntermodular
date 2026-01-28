package com.project.proyectointermodularapp.ui.screen

import com.project.proyectointermodularapp.ui.components.BottomMenu
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.project.proyectointermodularapp.ui.screen.nav.AppNavigation
import com.project.proyectointermodularapp.ui.screen.nav.Route

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val currentRoute = navController
        .currentBackStackEntryFlow
        .collectAsState(initial = navController.currentBackStackEntry)
        .value
        ?.destination
        ?.route

    val selectedIndex = when (currentRoute) {
        Route.Home.route -> Route.Home.index
        Route.Articles.route -> Route.Articles.index
        else -> 0
    }

    Scaffold(
        bottomBar = {
            BottomMenu(
                selectedIndex = selectedIndex,
                onItemSelected = { index ->
                    val route = when (index) {
                        Route.Home.index -> Route.Home.route
                        Route.Articles.index -> Route.Articles.route
                        else -> Route.Home.route
                    }

                    navController.navigate(route) {
                        popUpTo(Route.Home.route)
                        launchSingleTop = true
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            AppNavigation(navController)
        }
    }
}
