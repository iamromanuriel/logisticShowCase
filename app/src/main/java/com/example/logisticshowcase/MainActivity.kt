package com.example.logisticshowcase

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.logisticshowcase.ui.nav.NavigationGraph
import com.example.logisticshowcase.ui.screen.home.HomeScreen
import com.example.logisticshowcase.ui.screen.login.LoginScreen
import com.example.logisticshowcase.ui.screen.order_detail.OrderDetailScreen
import com.example.logisticshowcase.ui.theme.LogisticShowCaseTheme
import com.google.maps.android.compose.GoogleMap
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

        }else{
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        }

        enableEdgeToEdge()
        setContent {
            LogisticShowCaseTheme {
                NavigationGraph()
            }
        }
    }

}


@Composable
fun GoogleMapSample() {
    GoogleMap { }
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