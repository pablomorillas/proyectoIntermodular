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
import com.project.proyectointermodularapp.R
import com.project.proyectointermodularapp.ui.components.BottomMenu

enum class AppScreen(val bottomIndex: Int? = null) {
    Login,
    Register,

    Home(bottomIndex = 0),
    Articles(bottomIndex = 1),
    MyRequests(bottomIndex = 2),
    CompanyRequests(bottomIndex = 3),

    ArticleDetail;

    companion object {
        const val ARTICLE_ID_ARG = "articleId"
        private val articleDetailBaseRoute = ArticleDetail.name
        val articleDetailRoute = "$articleDetailBaseRoute/{$ARTICLE_ID_ARG}"

        fun createArticleDetailRoute(articleId: Int): String = "$articleDetailBaseRoute/$articleId"
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
    articleViewModel: ArticleViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by articleViewModel.uiState.collectAsState()
    val companyRequestsCount = uiState.articles.sumOf { it.responses.size }

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
        AppScreen.Articles,
        AppScreen.MyRequests,
        AppScreen.CompanyRequests,
        AppScreen.ArticleDetail
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
                        AppScreen.Articles,
                        AppScreen.MyRequests,
                        AppScreen.CompanyRequests -> currentScreen.bottomIndex

                        AppScreen.ArticleDetail -> when (previousScreen) {
                            AppScreen.Home -> AppScreen.Home.bottomIndex
                            AppScreen.Articles -> AppScreen.Articles.bottomIndex
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

                            AppScreen.Articles.bottomIndex -> {
                                navController.navigate(AppScreen.Articles.name) {
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
                    onLoginClick = { _, _ ->
                        navController.navigate(AppScreen.Home.name) {
                            popUpTo(AppScreen.Login.name) { inclusive = true }
                        }
                    },
                    onRegisterClick = {
                        navController.navigate(AppScreen.Register.name)
                    },
                    onGuestClick = {
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
                    onArticleClick = { articleId ->
                        navController.navigate(AppScreen.createArticleDetailRoute(articleId))
                    }
                )
            }

            composable(route = AppScreen.Articles.name) {
                ArticlesScreen()
            }

            composable(route = AppScreen.MyRequests.name) {
                MyRequestsScreen()
            }

            composable(route = AppScreen.CompanyRequests.name) {
                InboxScreen()
            }

            composable(
                route = AppScreen.articleDetailRoute,
                arguments = listOf(navArgument(AppScreen.ARTICLE_ID_ARG) { type = NavType.IntType })
            ) { backStackEntry ->
                val articleId = backStackEntry.arguments?.getInt(AppScreen.ARTICLE_ID_ARG)
                    ?: return@composable
                ArticleDetailScreen(articleId = articleId)
            }
        }
    }
}
