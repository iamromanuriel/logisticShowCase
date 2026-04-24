package com.example.logisticshowcase.ui.screen.order_detail

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.logisticshowcase.data.db.entity.OrderEntity
import com.example.logisticshowcase.data.db.entity.OrderItemEntity
import com.example.logisticshowcase.data.deliveryManager.DeliveryState
import com.example.logisticshowcase.data.deliveryManager.TimelineStep
import com.example.logisticshowcase.data.deliveryManager.getGenericTimeline
import com.example.logisticshowcase.data.model.OrderDetail
import com.example.logisticshowcase.data.model.OrderItem
import com.example.logisticshowcase.util.location.launchNavigation
import com.google.gson.Gson
import kotlinx.coroutines.delay

// ─────────────────────────────────────────────────────────────────────────────
// Data models
// ─────────────────────────────────────────────────────────────────────────────


@Composable
fun OrderDetailScreen(
    navController: NavController,
    viewModel: OrderDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val intent: (OrderDetailIntent) -> Unit = remember { viewModel::onIntent }
    val onBack: () -> Unit = remember { { navController.popBackStack() } }

    LaunchedEffect(state) {
        Log.d("OrderDetailScreen", "state: ${Gson().toJson(state.orderDetail)}")
    }

    OrderDetailScreen(state = state, intent = intent, onBackNavigation = onBack)
}

// ─────────────────────────────────────────────────────────────────────────────
// Stateless screen
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderDetailScreen(
    state: OrderDetailState,
    intent: (OrderDetailIntent) -> Unit = {},
    onBackNavigation: () -> Unit = {}
) {
    var entered by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { entered = true }

    Scaffold(
        topBar = {
            AnimatedOrderTopBar(
                orderId =  "referencia de lugar—",
                entered = entered,
                onBack = onBackNavigation,
                onFinish = { }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { innerPadding ->

        LazyColumn(
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 24.dp,
                start = 16.dp,
                end = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Hero card ──────────────────────────────────────────────────
            item {
                AnimatedVisibility(
                    visible = entered,
                    enter = fadeIn(tween(400)) + slideInVertically(tween(400)) { -40 }
                ) {
                    state.let { CardProgressOrder(orderDetail = it) }
                }
            }

            // ── Product items (staggered) ──────────────────────────────────
            itemsIndexed(state.products) { index, item ->
                StaggeredItem(index = index, entered = entered) {
                    ProductItem(item)
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Top bar
// ─────────────────────────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedOrderTopBar(
    orderId: String,
    entered: Boolean,
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    AnimatedVisibility(
        visible = entered,
        enter = fadeIn(tween(300)) + slideInVertically(tween(350)) { -60 }
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            },
            title = {
                Column {
                    Text(
                        text = "Orden #$orderId",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Seguimiento en tiempo real",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            actions = {
                FilledTonalButton(
                    onClick = onFinish,
                    modifier = Modifier.padding(end = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Finalizar")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
            )
        )
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Hero card — address + timeline
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun CardProgressOrder(orderDetail: OrderDetailState) {
    val context = LocalContext.current

    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // ── Gradient header band ───────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        ),
                        RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Location chip
                    FilledIconButton(
                        onClick = {
                            context.launchNavigation(orderDetail.client?.longitude?: 0.0, orderDetail.client?.latitude?: 0.0)
                        },
                        modifier = Modifier.size(40.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Abrir en mapa",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Column {
                        Text(
                            text = orderDetail.client?.address?: "",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = "Dirección de entrega",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .7f)
                        )
                    }
                }
            }

            // ── Client row ────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
                Column {
                    Text(
                        text = orderDetail.client?.name?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "Cliente",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant
            )

            // ── Timeline ──────────────────────────────────────────────────
            AnimatedTimeline(
                steps = getGenericTimeline(
                    currentState = DeliveryState.Idle
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Animated timeline
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun AnimatedTimeline(steps: List<TimelineStep>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        steps.forEachIndexed { index, step ->
            AnimatedTimelineItem(
                step = step,
                index = index,
                isLast = index == steps.size - 1
            )
        }
    }
}

@Composable
private fun AnimatedTimelineItem(
    step: TimelineStep,
    index: Int,
    isLast: Boolean
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(index * 120L)   // stagger per step
        visible = true
    }

    val dotColor = when {
        step.isCompleted -> MaterialTheme.colorScheme.primary
        step.isActive -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.outlineVariant
    }

    // Pulse animation for the active dot
    val pulseScale by rememberInfiniteTransition(label = "pulse").animateFloat(
        initialValue = 1f,
        targetValue = if (step.isActive) 1.3f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "dotPulse"
    )

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(300)) + slideInHorizontally(tween(300)) { -30 }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // ── Left rail ──────────────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.width(28.dp)
            ) {
                // Outer ring for active/completed states
                Box(contentAlignment = Alignment.Center) {
                    if (step.isActive) {
                        Box(
                            modifier = Modifier
                                .size((16 * pulseScale).dp)
                                .background(
                                    dotColor.copy(alpha = 0.25f),
                                    CircleShape
                                )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(dotColor, CircleShape)
                    )
                    if (step.isCompleted) {
                        // Checkmark — tiny inner white dot
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                }

                if (!isLast) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .padding(vertical = 2.dp)
                            .background(
                                if (step.isCompleted)
                                    MaterialTheme.colorScheme.primary.copy(alpha = .4f)
                                else
                                    MaterialTheme.colorScheme.outlineVariant,
                                RoundedCornerShape(1.dp)
                            )
                    )
                }
            }

            // ── Content ────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .padding(start = 14.dp, bottom = 20.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = step.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (step.isActive) FontWeight.Bold else FontWeight.Normal,
                    color = when {
                        step.isActive -> MaterialTheme.colorScheme.onSurface
                        step.isCompleted -> MaterialTheme.colorScheme.onSurface
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = step.subtitle,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Active badge
                if (step.isActive) {
                    Spacer(Modifier.height(6.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "En progreso",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Staggered list wrapper
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun StaggeredItem(
    index: Int,
    entered: Boolean,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(entered) {
        if (entered) {
            delay(300L + index * 80L)
            visible = true
        }
    }
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(tween(350)) + slideInVertically(tween(350)) { 40 }
    ) {
        content()
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Product item card
// ─────────────────────────────────────────────────────────────────────────────

@Composable
fun ProductItem(
    orderItem: OrderItemEntity
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Placeholder thumbnail
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondaryContainer)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = orderItem.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Descripción breve",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Text(
                text = "$ ${ orderItem.prince }",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}