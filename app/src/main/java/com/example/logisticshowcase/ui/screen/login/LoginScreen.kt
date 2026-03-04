package com.example.logisticshowcase.ui.screen.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
){

    val state by viewModel.state.collectAsStateWithLifecycle()
    val onChangeUserName: (String) -> Unit = remember(viewModel) {  viewModel::onChangeUserName }
    val onChangePassword: (String) -> Unit = remember(viewModel) { viewModel::onChangePassword }
    val onLogin: () -> Unit = remember(viewModel) { viewModel::onLogin }

    LoginScreen(state = state, onChangeUserName = onChangeUserName, onChangePassword = onChangePassword)
}

@Composable
private fun LoginScreen(state: LoginState, onChangeUserName: (String) -> Unit, onChangePassword: (String) -> Unit){
    Scaffold(

    ) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            TitleAndDescriptionLogin()
            LoginControls(
                state = state,
                onChangeUserName = onChangeUserName,
                onChangePassword = onChangePassword,
            )
        }
    }
}

@Composable
fun TitleAndDescriptionLogin(){
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxWidth().height(100.dp))
        Text(
            text = "Inicio de sesión",
            style = MaterialTheme.typography.headlineMedium,

        )
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        Text(
            text = "Por favor ingrese sus credenciales para continuar, y poder acceder a su cuenta.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))


    }
}

@Composable
fun LoginControls(
    state: LoginState,
    onChangeUserName: (String) -> Unit = {},
    onChangePassword: (String) -> Unit = {},
){
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.userName,
        onValueChange = onChangeUserName,
        label = { Text("Nombre de usuario") },
    )
    Spacer(modifier = Modifier.fillMaxWidth().height(20.dp))
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = state.password,
        onValueChange = onChangePassword,
        label = { Text("contraseña") },
    )

    Button(
        onClick = { },
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Text("Iniciar sesión", modifier = Modifier.padding(vertical = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview(){
    LoginScreen(state = LoginState(), onChangeUserName = {}, onChangePassword = {})
}