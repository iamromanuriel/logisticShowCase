package com.example.logisticshowcase.ui.screen.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, "Atras")
                    }
                },
                title = { Text("Chat Screen", style = MaterialTheme.typography.bodyMedium) },
                actions = {
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(imageVector = Icons.Outlined.MoreVert, "Mas opciones")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column (modifier = Modifier.padding(innerPadding)) {
            Text(text = "Chat Screen", modifier = Modifier.padding(innerPadding))
        }


    }
}

@Composable
@Preview(showBackground = true)
private fun ChatScreenPreview(){
    ChatScreen()
}