package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.project.proyectointermodularapp.domain.model.ViewerSession
import com.project.proyectointermodularapp.R
import com.project.proyectointermodularapp.ui.components.BottomMenu

enum class AppScreen(val bottomIndex: Int? = null) {
    Login,
    Register,

    Home(bottomIndex = 0),
    Requests(bottomIndex = 1),
    MyRequests(bottomIndex = 2),
    CompanyRequests(bottomIndex = 3),

    RequestDetail;

    companion object {
        const val REQUEST_ID_ARG = "requestId"
        private val requestDetailBaseRoute = RequestDetail.name
        val requestDetailRoute = "$requestDetailBaseRoute/{$REQUEST_ID_ARG}"

        fun createRequestDetailRoute(requestId: Int): String = "$requestDetailBaseRoute/$requestId"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectoIntermodularAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun MainScreen(
    requestViewModel: RequestViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by requestViewModel.uiState.collectAsState()
    val isGuest = uiState.viewerSession == ViewerSession.Invitado
    val companyRequestsCount = uiState.requests.sumOf { it.responses.size }

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteName = currentBackStackEntry?.destination?.route?.substringBefore("/")
        ?: AppScreen.Login.name
    val currentScreen = runCatching { AppScreen.valueOf(currentRouteName) }
        .getOrDefault(AppScreen.Login)

    val previousRouteName = navController.previousBackStackEntry?.destination?.route?.substringBefore("/")
    val previousScreen = previousRouteName
        ?.let { runCatching { AppScreen.valueOf(it) }.getOrNull() }

    val showBottomBar = currentScreen in setOf(
        AppScreen.Home,
        AppScreen.Requests,
        AppScreen.MyRequests,
        AppScreen.CompanyRequests,
        AppScreen.RequestDetail
    )

    Scaffold(
        topBar = {
            ProyectoIntermodularAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        },
        bottomBar = {
            if (showBottomBar) {
                BottomMenu(
                    selectedIndex = when (currentScreen) {
                        AppScreen.Home,
                        AppScreen.Requests,
                        AppScreen.MyRequests,
                        AppScreen.CompanyRequests -> currentScreen.bottomIndex

                        AppScreen.RequestDetail -> when (previousScreen) {
                            AppScreen.Home -> AppScreen.Home.bottomIndex
                            AppScreen.Requests -> AppScreen.Requests.bottomIndex
                            AppScreen.MyRequests -> AppScreen.MyRequests.bottomIndex
                            AppScreen.CompanyRequests -> AppScreen.CompanyRequests.bottomIndex
                            else -> AppScreen.Home.bottomIndex
                        }

                        else -> null
                    },
                    companyRequestsCount = companyRequestsCount,
                    onItemSelected = { index ->
                        when (index) {
                            AppScreen.Home.bottomIndex -> {
                                navController.navigate(AppScreen.Home.name) {
                                    popUpTo(AppScreen.Home.name) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            AppScreen.Requests.bottomIndex -> {
                                navController.navigate(AppScreen.Requests.name) {
                                    popUpTo(AppScreen.Home.name) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            AppScreen.MyRequests.bottomIndex -> {
                                if (isGuest) {
                                    navController.navigate(AppScreen.Login.name) {
                                        popUpTo(AppScreen.Login.name) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(AppScreen.MyRequests.name) {
                                        popUpTo(AppScreen.Home.name) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }

                            AppScreen.CompanyRequests.bottomIndex -> {
                                if (isGuest) {
                                    navController.navigate(AppScreen.Login.name) {
                                        popUpTo(AppScreen.Login.name) { inclusive = true }
                                        launchSingleTop = true
                                    }
                                } else {
                                    navController.navigate(AppScreen.CompanyRequests.name) {
                                        popUpTo(AppScreen.Home.name) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = AppScreen.Login.name,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = AppScreen.Login.name) {
                LoginScreen(
                    onLoginClick = { _, _ ->
                        requestViewModel.setViewerSession(ViewerSession.Cliente(id = 1))
                        navController.navigate(AppScreen.Home.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(AppScreen.Register.name)
                    },
                    onGuestClick = {
                        requestViewModel.setViewerSession(ViewerSession.Invitado)
                        navController.navigate(AppScreen.Home.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                        }
                    }
                )
            }

            composable(route = AppScreen.Register.name) {
                RegisterScreen(
                    onLoginClick = {
                        if (!navController.popBackStack()) {
                            navController.navigate(AppScreen.Login.name) {
                                launchSingleTop = true
                            }
                        }
                    }
                )
            }

            composable(route = AppScreen.Home.name) {
                HomeScreen(
                    onRequestClick = { requestId ->
                        navController.navigate(AppScreen.createRequestDetailRoute(requestId))
                    },
                    viewModel = requestViewModel
                )
            }

            composable(route = AppScreen.Requests.name) {
                RequestsScreen(
                    onRequestClick = { requestId ->
                        navController.navigate(AppScreen.createRequestDetailRoute(requestId))
                    },
                    viewModel = requestViewModel
                )
            }

            composable(route = AppScreen.MyRequests.name) {
                if (isGuest) {
                    LaunchedEffect(Unit) {
                        navController.navigate(AppScreen.Login.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                } else {
                    MyRequestsScreen(
                        onRequestClick = { requestId ->
                            navController.navigate(AppScreen.createRequestDetailRoute(requestId))
                        },
                        viewModel = requestViewModel
                    )
                }
            }

            composable(route = AppScreen.CompanyRequests.name) {
                if (isGuest) {
                    LaunchedEffect(Unit) {
                        navController.navigate(AppScreen.Login.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                } else {
                    InboxScreen(viewModel = requestViewModel)
                }
            }

            composable(
                route = AppScreen.requestDetailRoute,
                arguments = listOf(navArgument(AppScreen.REQUEST_ID_ARG) { type = NavType.IntType })
            ) { backStackEntry ->
                val requestId = backStackEntry.arguments?.getInt(AppScreen.REQUEST_ID_ARG)
                    ?: return@composable
                RequestDetailScreen(
                    requestId = requestId,
                    viewModel = requestViewModel
                )
            }
        }
    }
}
