package com.project.proyectointermodularapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.proyectointermodularapp.R
import com.project.proyectointermodularapp.ui.theme.AlmosWhite
import com.project.proyectointermodularapp.ui.theme.Grey
import com.project.proyectointermodularapp.ui.theme.Red

@Composable
fun LoginScreen(
    loginErrorMessage: String? = null,
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    onGuestClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    fun validate(): Boolean {
        var valid = true

        if (!isValidEmail(email)) {
            emailError = "Email no valido"
            valid = false
        } else {
            emailError = null
        }

        if (password.length < 6) {
            passwordError = "Minimo 6 caracteres"
            valid = false
        } else {
            passwordError = null
        }

        return valid
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo_app_invertido_sin_fondo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(140.dp)
                .padding(bottom = 30.dp)
        )

        Text(
            text = "Iniciar sesion",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = { Text("Email") },
            isError = emailError != null,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                emailError?.let { Text(it) }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = null
            },
            label = { Text("Contrasena") },
            isError = passwordError != null,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                passwordError?.let { Text(it) }
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        loginErrorMessage?.let { message ->
            Text(
                text = message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
        }

        Button(
            onClick = {
                if (validate()) {
                    onLoginClick(email, password)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Red,
                contentColor = AlmosWhite,
                disabledContainerColor = Grey
            )
        ) {
            Text("Iniciar sesion")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onGuestClick,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Red)
        ) {
            Text("Entrar como invitado")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row {
            Text(
                color = Grey,
                modifier = Modifier.paddingFromBaseline(5.dp),
                text = "No tienes una cuenta? "
            )
            Text(
                color = Red,
                modifier = Modifier
                    .paddingFromBaseline(5.dp)
                    .clickable { onRegisterClick() },
                text = "Registrate."
            )
        }
    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            onLoginClick = { username, password ->
                println("Usuario: $username, Password: $password")
            },
            onRegisterClick = {},
            onGuestClick = {}
        )
    }
}
