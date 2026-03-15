package com.example.logisticshowcase.ui.screen.order_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.ui.components.ProductItem
import com.example.logisticshowcase.util.location.launchNavigation

@Composable
fun OrderDetailScreen(
    navController: NavController,
    viewModel: OrderDetailViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (OrderDetailIntent) -> Unit = remember {
         viewModel::onIntent
    }

    val onBackNavigation: () -> Unit = remember {
        { navController.popBackStack() }
    }
    OrderDetailScreen(
        state = state,
        intent = intent,
        onBackNavigation = onBackNavigation
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderDetailScreen(
    state: OrderDetailState,
    intent: (OrderDetailIntent) -> Unit = {},
    onBackNavigation: () -> Unit = {}
){

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackNavigation
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            null
                        )
                    }
                },
                title = {
                    Text("78427843")
                },
                actions = {
                    TextButton(
                        onClick = {}
                    ) {
                        Text("Finalizar")
                    }
                }
            )

        }
    ) { innerContent ->

        LazyColumn(
            contentPadding = innerContent
        ) {
            item {
                state.orderDetail?.let {
                    CardProgressOrder(orderDetail = it)
                }

            }

            items(4){
                ProductItem()
            }
        }
    }
}


@Composable
fun CardProgressOrder(orderDetail: OrderDetail){
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                IconButton(
                    onClick = {
                        context.launchNavigation(orderDetail.latitude, orderDetail.longitude)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        null
                    )
                }

                Text(text = orderDetail.addressName, style = MaterialTheme.typography.titleMedium)
            }

            Text(orderDetail.clientName, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(horizontal = 10.dp))
        }

        StaticTimeline(steps = mySteps)

    }
}

data class TimelineStep(
    val title: String,
    val subtitle: String,
    val isActive: Boolean,
    val isCompleted: Boolean
)

@Composable
fun StaticTimeline(steps: List<TimelineStep>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        steps.forEachIndexed { index, step ->
            TimelineItem(
                step = step,
                isLast = index == steps.size - 1
            )
        }
    }
}

@Composable
fun TimelineItem(
    step: TimelineStep,
    isLast: Boolean
) {
    val dotColor = when {
        step.isCompleted -> Color(0xFF4CAF50) // Verde
        step.isActive -> Color(0xFF2196F3)    // Azul
        else -> Color.LightGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {
            // El círculo (Nodo)
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(dotColor, CircleShape)
            )

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(2.dp)
                        .background(Color.LightGray)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(start = 12.dp, bottom = 24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = step.title,
                fontWeight = if (step.isActive) FontWeight.Bold else FontWeight.Normal,
                fontSize = 16.sp,
                color = if (step.isActive || step.isCompleted) Color.Black else Color.Gray
            )
            Text(
                text = step.subtitle,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

val mySteps = listOf(
    TimelineStep("Pedido Recibido", "Estamos preparando tu orden", isCompleted = true, isActive = false),
    TimelineStep("En Cocina", "El chef está en ello", isCompleted = true, isActive = false),
    TimelineStep("Repartidor en camino", "Tu comida va volando", isCompleted = false, isActive = true),
    TimelineStep("Entregado", "¡Buen provecho!", isCompleted = false, isActive = false)
)




@Preview(showBackground = true)
@Composable
private fun OrderDetailScreenPreview(){
    OrderDetailScreen(OrderDetailState())
}