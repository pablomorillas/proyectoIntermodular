package com.project.proyectointermodularapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val AppColorScheme = lightColorScheme(
    primary = Red,
    onPrimary = AlmosWhite,
    secondary = Grey,
    onSecondary = AlmosWhite,
    tertiary = Black,
    onTertiary = AlmosWhite,
    background = AlmosWhite,
    onBackground = Black,
    surface = AlmosWhite,
    onSurface = Black,
    surfaceVariant = AlmosWhite,
    onSurfaceVariant = Black,
    error = Red,
    onError = AlmosWhite
)

@Composable
fun ProyectoIntermodularAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
