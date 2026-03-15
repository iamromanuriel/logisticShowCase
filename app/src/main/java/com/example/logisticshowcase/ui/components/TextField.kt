package com.example.logisticshowcase.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldBase(
    modifier : Modifier = Modifier,
    value: String,
    onChangeValue: (String) -> Unit,
    label: String? = null
){
    TextField(
        shape = RoundedCornerShape(size = 10.dp),
        modifier = modifier,
        value = value,
        onValueChange = onChangeValue,
        singleLine = true,
        label = {
            label?.let { Text(text = it) }
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldBasePreview(){
    TextFieldBase(value = "dasd", onChangeValue = {}, label = "Hola")

}