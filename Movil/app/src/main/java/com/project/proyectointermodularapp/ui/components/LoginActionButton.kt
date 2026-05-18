package com.project.proyectointermodularapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun LoginActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Iniciar sesion"
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Red,
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}
