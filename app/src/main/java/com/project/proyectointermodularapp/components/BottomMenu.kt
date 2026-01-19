import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.proyectointermodularapp.R

@Composable
fun BottomMenu(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    val customRed = Color(0xFFC61313)
    val customWhite = Color.White

    // Contenedor principal con fondo rojo para que cubra toda el área inferior
    Box(modifier = Modifier.background(customRed)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp) // Aumentamos la altura para iconos grandes
                .navigationBarsPadding()
                .background(customRed),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val items = listOf(
                R.drawable.home,
                R.drawable.article_person,
                R.drawable.document_search,
                R.drawable.mail
            )

            items.forEachIndexed { index, iconRes ->
                val isSelected = selectedIndex == index

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            if (isSelected) customWhite else customRed,
                            RectangleShape // Cuadrado perfecto sin bordes redondeados
                        )
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = null,
                        // Tamaño equilibrado para una barra de 80dp
                        modifier = Modifier.size(45.dp),
                        tint = if (isSelected) customRed else customWhite
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomMenuPreview() {
    var selectedIndex by remember { mutableStateOf(1) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomMenu(
                    selectedIndex = selectedIndex,
                    onItemSelected = { selectedIndex = it }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Text("Pantalla Actual: $selectedIndex")
            }
        }
    }
}