package com.example.logisticshowcase.ui.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.logisticshowcase.ui.components.GetLocationPermission
import com.example.logisticshowcase.ui.nav.NavigationGraph
import com.example.logisticshowcase.ui.screen.login.LoginScreen
import com.example.logisticshowcase.ui.theme.LogisticShowCaseTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.FirebaseApp
import com.google.maps.android.compose.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            LogisticShowCaseTheme {
                val mainState by viewModel.state.collectAsStateWithLifecycle()
                val localPermission = rememberPermissionState(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                if(mainState.userLogIn){
                    if(localPermission.status.isGranted){
                        NavigationGraph()
                    }else{
                        GetLocationPermission()
                    }
                }else{
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LogisticShowCaseTheme {
        Greeting("Android")
    }
}