package com.example.logisticshowcase.ui.screen.map_deliver


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.model.OrderItem
import com.example.logisticshowcase.ui.components.OrderItemCard
import com.example.logisticshowcase.ui.nav.DetailOrder
import com.example.logisticshowcase.util.RememberedGoogleMap
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapDeliveryScreen(
    navController: NavController,
    viewModel: MapDeliveryViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigationDetail: (Int) -> Unit = remember {
        { navController.navigate(DetailOrder(1)) }
    }

    MapDeliveryScreen(state = state, onNavigationDetail = navigationDetail)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapDeliveryScreen(
    state: MapDeliveryState,
    onNavigationDetail: (Int) -> Unit = {}
) {

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(state.location, 5f)
    }

    LaunchedEffect(state.location) {
        cameraPosition.animate(
            update = CameraUpdateFactory.newLatLngZoom(state.location, 13f),
            durationMs = 1000
        )
    }
    val modalState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val mapUiSettings = remember {
        MapUiSettings(zoomControlsEnabled = false)
    }
    val mapProperties = remember {
        MapProperties(isMyLocationEnabled = true)
    }

    var showClientInfo by remember { mutableStateOf(false) }
    var showModalOrder by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showModalOrder = true }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
            uiSettings = mapUiSettings,
            properties = mapProperties
        ) {

            state.clients.forEach { client ->
                key (client.id){
                    MarkerComposable(
                        state = remember(client.id) {
                            MarkerState(position = client.toLatLng())
                        },
                        onClick = { marker -> showClientInfo = true; true }
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }

        }

        if (showModalOrder) {
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                sheetState = modalState,
                onDismissRequest = {
                    showModalOrder = false
                }
            ) {
                LayoutItemOrders(
                    orders = state.orders,
                    innerPaddingValues = innerContent,
                    onNavigationDetail = {
                        onNavigationDetail(it)
                        showModalOrder = false
                    })
            }
        }

        if(showClientInfo){
            ModalBottomSheet(
                containerColor = MaterialTheme.colorScheme.background,
                sheetState = modalState,
                onDismissRequest = {
                    showClientInfo = false
                }
            ) {
                LayoutInfoOrderDetail{
                    onNavigationDetail(1)
                    showClientInfo = false
                }
            }
        }
    }
}


@Composable
fun LayoutItemOrders(
    orders: List<OrderEntity>,
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    onNavigationDetail: (Int) -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = innerPaddingValues.calculateTopPadding() + 8.dp,
            bottom = innerPaddingValues.calculateBottomPadding() + 16.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(orders) { index, order ->
            // Staggered entrance: cada item entra con un pequeño retardo
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(Unit) {
                delay(index * 60L)
                visible = true
            }

            AnimatedVisibility (
                visible = visible,
                enter = fadeIn(tween (350)) + slideInVertically (
                    initialOffsetY = { 40 },
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                OrderItemCard (
                    order = order,
                    onClick = { onNavigationDetail(order.id) }
                )
            }
        }
    }
}

@Composable
fun LayoutInfoOrderDetail(
    onNavDetail: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.size(10.dp))
        Icon(
            imageVector = Icons.Default.LocationOn,
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null,
            modifier = Modifier.size(60.dp)
        )

        Spacer(modifier = Modifier.size(10.dp))
        Text(
            text = "En espera",
            style = MaterialTheme.typography.titleSmall,
        )
        Spacer(modifier = Modifier.size(10.dp))

        Text(
            text = "Casa de Pepe",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(10.dp))

        Button(
            modifier = Modifier.fillMaxWidth(0.5f).padding(bottom = 10.dp),
            onClick = onNavDetail
        ) {
            Text(text = "Ir a detalle")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MapDeliveryScreenPreview() {
    MapDeliveryScreen(state = MapDeliveryState())
}