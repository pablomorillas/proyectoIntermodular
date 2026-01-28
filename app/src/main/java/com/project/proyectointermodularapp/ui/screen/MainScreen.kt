package com.project.proyectointermodularapp.ui.screen

import com.project.proyectointermodularapp.ui.components.BottomMenu
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.proyectointermodularapp.ui.screen.nav.AppNavigation
import com.project.proyectointermodularapp.ui.screen.nav.Route

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Route.Home.route,
        Route.Articles.route
    )

    val selectedIndex = when (currentRoute) {
        Route.Home.route -> Route.Home.index
        Route.Articles.route -> Route.Articles.index
        else -> 0
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomMenu(
                    selectedIndex = when (currentRoute) {
                        Route.Home.route -> Route.Home.index
                        Route.Articles.route -> Route.Articles.index
                        else -> null
                    },
                    onItemSelected = { index ->
                        when (index) {
                            Route.Home.index -> {
                                navController.navigate(Route.Home.route) {
                                    popUpTo(Route.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            Route.Articles.index -> {
                                navController.navigate(Route.Articles.route) {
                                    popUpTo(Route.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { padding ->
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(padding)
        )
    }
}
