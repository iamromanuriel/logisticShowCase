package com.example.logisticshowcase.ui.screen.order_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun OrderDetailScreen(
    viewModel: OrderDetailViewModel = hiltViewModel()
){
    OrderDetailScreen("")
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderDetailScreen(state: Any){

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Order Detail")
                }
            )
        }
    ) { innerContent ->

        Column(
            modifier = Modifier.padding(innerContent)
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDetailScreenPreview(){
    OrderDetailScreen("")
}