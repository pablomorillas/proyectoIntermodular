package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
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
import com.project.proyectointermodularapp.ui.components.LoginActionButton
import com.project.proyectointermodularapp.ui.theme.Grey

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
    requestViewModel: RequestViewModel = viewModel(factory = RequestViewModel.Factory),
    navController: NavHostController = rememberNavController()
) {
    val uiState by requestViewModel.uiState.collectAsState()
    val isGuest = uiState.viewerSession == ViewerSession.Invitado
    val companyRequestsCount = uiState.requests.sumOf { it.responses.size }
    var showCreateRequestGuestPrompt by remember { mutableStateOf(false) }
    var postLoginRoute by remember { mutableStateOf<String?>(null) }
    var loginErrorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRouteName = currentBackStackEntry?.destination?.route?.substringBefore("/")
        ?: AppScreen.Login.name
    val currentScreen = runCatching { AppScreen.valueOf(currentRouteName) }
        .getOrDefault(AppScreen.Login)

    val previousRouteName = navController.previousBackStackEntry?.destination?.route?.substringBefore("/")
    val previousScreen = previousRouteName
        ?.let { runCatching { AppScreen.valueOf(it) }.getOrNull() }

    val canNavigateBack = currentScreen == AppScreen.Register ||
        currentScreen == AppScreen.RequestDetail

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
                canNavigateBack = canNavigateBack,
                navigateUp = {
                    if (!navController.popBackStack()) {
                        navController.navigate(AppScreen.Home.name) {
                            popUpTo(AppScreen.Home.name) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
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
                                navController.navigate(AppScreen.MyRequests.name) {
                                    popUpTo(AppScreen.Home.name) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }

                            AppScreen.CompanyRequests.bottomIndex -> {
                                navController.navigate(AppScreen.CompanyRequests.name) {
                                    popUpTo(AppScreen.Home.name) { saveState = true }
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
        NavHost(
            navController = navController,
            startDestination = AppScreen.Login.name,
            modifier = Modifier.padding(padding)
        ) {
            composable(route = AppScreen.Login.name) {
                LoginScreen(
                    loginErrorMessage = loginErrorMessage,
                    onLoginClick = { email, password ->
                        if (uiState.isLoading) {
                            loginErrorMessage = "Espera un momento mientras se cargan los datos."
                            return@LoginScreen
                        }
                        coroutineScope.launch {
                            val success = requestViewModel.login(email, password)
                            if (success) {
                                loginErrorMessage = null
                                val destination = postLoginRoute ?: AppScreen.Home.name
                                postLoginRoute = null
                                navController.navigate(destination) {
                                    popUpTo(AppScreen.Login.name) { inclusive = true }
                                    launchSingleTop = true
                                }
                            } else {
                                loginErrorMessage = "Email o contrasena incorrectos."
                            }
                        }
                    },
                    onRegisterClick = {
                        loginErrorMessage = null
                        navController.navigate(AppScreen.Register.name)
                    },
                    onGuestClick = {
                        loginErrorMessage = null
                        postLoginRoute = null
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
                    },
                    onRegisterSubmit = { nombre, email, password ->
                        coroutineScope.launch {
                            val newClient = requestViewModel.register(nombre, email, password)
                            if (newClient != null) {
                                navController.navigate(AppScreen.Login.name) {
                                    popUpTo(AppScreen.Register.name) { inclusive = true }
                                    launchSingleTop = true
                                }
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
                    onCreateRequestClick = {
                        if (isGuest) {
                            showCreateRequestGuestPrompt = true
                        } else {
                            navController.navigate(AppScreen.MyRequests.name) {
                                launchSingleTop = true
                            }
                        }
                    },
                    onRequestClick = { requestId ->
                        navController.navigate(AppScreen.createRequestDetailRoute(requestId))
                    },
                    viewModel = requestViewModel
                )
            }

            composable(route = AppScreen.MyRequests.name) {
                if (isGuest) {
                    LoginRequiredAccessScreen(
                        message = "Para ver tus solicitudes debes iniciar sesion.",
                        onLoginClick = {
                            postLoginRoute = AppScreen.MyRequests.name
                            navController.navigate(AppScreen.Login.name) {
                                launchSingleTop = true
                            }
                        }
                    )
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
                    LoginRequiredAccessScreen(
                        message = "Para ver las respuestas debes iniciar sesion.",
                        onLoginClick = {
                            postLoginRoute = AppScreen.CompanyRequests.name
                            navController.navigate(AppScreen.Login.name) {
                                launchSingleTop = true
                            }
                        }
                    )
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
                    onNavigateToLogin = {
                        postLoginRoute = null
                        navController.navigate(AppScreen.Login.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    viewModel = requestViewModel
                )
            }
        }

        if (showCreateRequestGuestPrompt) {
            AlertDialog(
                onDismissRequest = { showCreateRequestGuestPrompt = false },
                title = { Text("Inicia sesion") },
                text = {
                    Text(
                        "Debes iniciar sesion para crear una solicitud."
                    )
                },
                confirmButton = {
                    LoginActionButton(
                        onClick = {
                            showCreateRequestGuestPrompt = false
                            postLoginRoute = AppScreen.MyRequests.name
                            navController.navigate(AppScreen.Login.name) {
                                launchSingleTop = true
                            }
                        }
                    )
                },
                dismissButton = {
                    Button(
                        onClick = { showCreateRequestGuestPrompt = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Grey,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Mas tarde")
                    }
                }
            )
        }
    }
}

@Composable
private fun LoginRequiredAccessScreen(
    message: String,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge
            )

            LoginActionButton(
                onClick = onLoginClick
            )
        }
    }
}
