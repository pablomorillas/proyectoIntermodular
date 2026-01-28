package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun InboxScreen (
    modifier: Modifier = Modifier,
    viewModel: ArticleViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column {
        Text(
            text = "Bandeja de entrada",
            style = MaterialTheme.typography.headlineLarge,
            color = Red,
            modifier = Modifier.padding(16.dp)
        )
    }
}