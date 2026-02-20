package com.example.logisticshowcase.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logisticshowcase.ui.screen.home.HomeScreen
import kotlinx.serialization.Serializable

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ) {
        composable<Home> {
            HomeScreen(

            )
        }


    }
}

@Serializable
object Home

@Serializable
data class DetailOrder(val id: Int)