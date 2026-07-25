package com.pathok.app.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pathok.app.theme.LocalPathokColors
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip

sealed class NavTab(val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Home : NavTab("হোম", Icons.Filled.Home)
    object Read : NavTab("পড়া", Icons.Filled.MenuBook)
    object Library : NavTab("লাইব্রেরি", Icons.Filled.LibraryBooks)
    object Profile : NavTab("প্রোফাইল", Icons.Filled.Person)
    object Settings : NavTab("সেটিং", Icons.Filled.Settings)
}

val allTabs = listOf(NavTab.Home, NavTab.Read, NavTab.Library, NavTab.Profile, NavTab.Settings)

@Composable
fun MainScreen() {
    val colors = LocalPathokColors.current
    var selectedTab by remember { mutableStateOf<NavTab>(NavTab.Home) }

    Scaffold(
        containerColor = colors.bg,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.barBg)
                    .padding(vertical = 8.dp, horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                allTabs.forEach { tab ->
                    val isSelected = selectedTab == tab
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { selectedTab = tab }
                            .background(if (isSelected) colors.accentGlow else Color.Transparent)
                            .padding(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = tab.icon,
                            contentDescription = tab.label,
                            tint = if (isSelected) colors.accent else colors.text2,
                            modifier = Modifier
                                .size(24.dp)
                        )
                        Text(
                            tab.label,
                            fontSize = 11.sp,
                            color = if (isSelected) colors.accent else colors.text2,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                is NavTab.Home -> PlaceholderScreen("হোম")
                is NavTab.Read -> PlaceholderScreen("পড়া")
                is NavTab.Library -> PlaceholderScreen("লাইব্রেরি")
                is NavTab.Profile -> PlaceholderScreen("প্রোফাইল")
                is NavTab.Settings -> PlaceholderScreen("সেটিং")
            }
        }
    }

}

@Composable
private fun PlaceholderScreen(name: String) {
    val colors = LocalPathokColors.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.bg),
        contentAlignment = Alignment.Center
    ) {
        Text("$name স্ক্রিন — পরের ধাপে আসবে", color = colors.text)
    }
}