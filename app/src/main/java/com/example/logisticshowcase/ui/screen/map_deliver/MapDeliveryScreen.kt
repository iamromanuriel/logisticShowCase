package com.example.logisticshowcase.ui.screen.map_deliver

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.logisticshowcase.ui.components.OrderItem
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapDeliveryScreen(viewModel: MapDeliveryViewModel = hiltViewModel()){

    val state by viewModel.state.collectAsStateWithLifecycle()

    MapDeliveryScreen(state = state)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapDeliveryScreen(state: MapDeliveryState){

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.location, 10f)
    }

    LaunchedEffect(state.location) {
        cameraPosition.animate(
            update = CameraUpdateFactory.newLatLngZoom(state.location, 15f),
            durationMs = 1000
        )
    }
    val modalState = rememberModalBottomSheetState (
        skipPartiallyExpanded = true
    )
    var showModalOrder by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton (
                onClick = { showModalOrder = true }
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Outlined.Info, "Mis pedidos"
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    Text("Pedidos")
                }
            }
        }
    ) { innerContent ->
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPosition,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false
            ),
            properties = MapProperties(
                isMyLocationEnabled = true
            )
        ) {

        }

        if(showModalOrder){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                sheetState = modalState,
                onDismissRequest = {
                    showModalOrder = false
                }
            ) {
                LayoutItemOrders(innerPaddingValues = innerContent)
            }
        }
    }
}


@Composable
fun LayoutItemOrders(
    innerPaddingValues: PaddingValues,

){
    LazyColumn(
        contentPadding = innerPaddingValues
    ) {
        items(5){
            OrderItem(
                onClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapDeliveryScreenPreview(){
    MapDeliveryScreen()
}