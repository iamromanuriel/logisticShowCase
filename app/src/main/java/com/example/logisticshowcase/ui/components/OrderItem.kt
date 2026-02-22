package com.example.logisticshowcase.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun OrderItem(
    onClick: () -> Unit
){
    ListItem(
        modifier = Modifier.clickable(
            onClick = onClick
        ),
        headlineContent = {
            Text("18 Poniente", style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            Text("Roman Uriel Francisco")
        },
        trailingContent = {
            Text("4 Piezas")
        }
    )
}