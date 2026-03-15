package com.example.logisticshowcase.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    HomeScreen("")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(state: String) {
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Home Screen") }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(innerPadding)) {
            Spacer(modifier = Modifier.height(10.dp))
            CardWelcome()
            Spacer(modifier = Modifier.height(10.dp))
            CardInformationTransport()
        }
    }
}

@Composable
fun CardWelcome() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Text("Hola", style = MaterialTheme.typography.headlineMedium)

            Column(modifier = Modifier) {
                Text("Bienvenido", style = MaterialTheme.typography.headlineMedium)
                Text("Roman", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
fun CardInformationTransport() {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Icon(
                modifier = Modifier.size(60.dp),
                imageVector = Icons.Default.Info,
                contentDescription = ""
            )


            Column(
                modifier = Modifier.padding()
            ) {
                Text("Transporte", style = MaterialTheme.typography.headlineMedium)
                Text("HIFU6776")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HomeScreen("")
}