package com.example.logisticshowcase.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.logisticshowcase.data.model.OrderItem


@Composable
fun OrderItem(
    order: OrderItem,
    onClick: () -> Unit
){
    ListItem(
        modifier = Modifier.clickable(
            onClick = onClick
        ),
        headlineContent = {
            Text(order.addressName, style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            Text(order.clientName)
        },
        trailingContent = {
            Text(order.itemCount.toString())
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ProductItem(){
    ListItem(
        headlineContent = {
            Text(text = "Garrafon de 20 litros Bonafont", style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            Text("Color rosa/rojo")
        },
        trailingContent = {
            Text("4 Piezas")
        }

    )
}