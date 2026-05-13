package com.project.proyectointermodularapp.ui.components

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.project.proyectointermodularapp.R

@Composable
fun BottomMenu(
    selectedIndex: Int?,
    companyRequestsCount: Int,
    onItemSelected: (Int) -> Unit
) {
    val customRed = Color(0xFFC61313)
    val customWhite = Color.White

    Box(modifier = Modifier.background(customRed)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .navigationBarsPadding()
                .background(customRed),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val items = listOf(
                0 to R.drawable.home,
                1 to R.drawable.document_search,
                2 to R.drawable.article_person,
                3 to R.drawable.mail
            )

            items.forEach { (index, iconRes) ->
                val isSelected = selectedIndex == index
                val badgeText = when {
                    index != 3 || companyRequestsCount <= 0 -> null
                    companyRequestsCount >= 10 -> "+9"
                    else -> companyRequestsCount.toString()
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            if (isSelected) customWhite else customRed,
                            RectangleShape
                        )
                        .clickable { onItemSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Box(modifier = Modifier.size(45.dp)) {
                        Icon(
                            painter = painterResource(id = iconRes),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            tint = if (isSelected) customRed else customWhite
                        )

                        if (badgeText != null) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(x = 8.dp, y = (-6).dp),
                                containerColor = Color(0xFF8B0E0E),
                                contentColor = customWhite
                            ) {
                                Text(
                                    text = badgeText,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BottomMenuPreview() {
    var selectedIndex by remember { mutableIntStateOf(1) }

    MaterialTheme {
        Scaffold(
            bottomBar = {
                BottomMenu(
                    selectedIndex = selectedIndex,
                    companyRequestsCount = 12,
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
