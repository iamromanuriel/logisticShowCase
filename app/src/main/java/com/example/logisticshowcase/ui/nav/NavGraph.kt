package com.example.logisticshowcase.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.logisticshowcase.ui.screen.home.HomeScreen
import com.example.logisticshowcase.ui.screen.map_deliver.MapDeliveryScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.serialization.Serializable

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Map
    ) {
        composable<Home> {
            HomeScreen(

            )
        }

        composable<Map> {


            MapDeliveryScreen()
        }

    }
}

@Serializable
object Home
@Serializable
object Map

@Serializable
data class DetailOrder(val id: Int)