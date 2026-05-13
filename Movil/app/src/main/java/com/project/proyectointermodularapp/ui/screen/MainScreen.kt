package com.project.proyectointermodularapp.ui.screen

import com.project.proyectointermodularapp.ui.components.BottomMenu
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.proyectointermodularapp.ui.screen.nav.AppNavigation
import com.project.proyectointermodularapp.ui.screen.nav.Route

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val articleViewModel: ArticleViewModel = viewModel()
    val uiState by articleViewModel.uiState.collectAsState()
    val companyRequestsCount = uiState.articles.sumOf { it.responses.size }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val previousRoute = navController.previousBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Route.Home.route,
        Route.Articles.route,
        Route.MyRequests.route,
        Route.CompanyRequests.route,
        Route.ArticleDetail.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomMenu(
                    selectedIndex = when (currentRoute) {
                        Route.Home.route -> Route.Home.index
                        Route.Articles.route -> Route.Articles.index
                        Route.MyRequests.route -> Route.MyRequests.index
                        Route.CompanyRequests.route -> Route.CompanyRequests.index
                        Route.ArticleDetail.route -> when (previousRoute) {
                            Route.Home.route -> Route.Home.index
                            Route.Articles.route -> Route.Articles.index
                            Route.MyRequests.route -> Route.MyRequests.index
                            Route.CompanyRequests.route -> Route.CompanyRequests.index
                            else -> Route.Home.index
                        }
                        else -> null
                    },
                    companyRequestsCount = companyRequestsCount,
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

                            Route.MyRequests.index -> {
                                navController.navigate(Route.MyRequests.route) {
                                    popUpTo(Route.Home.route) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            Route.CompanyRequests.index -> {
                                navController.navigate(Route.CompanyRequests.route) {
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
