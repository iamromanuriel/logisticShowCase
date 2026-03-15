package com.example.logisticshowcase.ui.screen.login

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.logisticshowcase.R
import com.example.logisticshowcase.ui.components.TextFieldBase
import com.example.logisticshowcase.util.message.toString

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val onIntent: (LoginIntent) -> Unit = remember { viewModel::onIntent }

    LoginScreen(
        state = state,
        onIntent = onIntent
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit = {}
) {
    Scaffold (
        containerColor = MaterialTheme.colorScheme.background
    ){ innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            TitleAndDescriptionLogin()
            LoginControls(
                state = state,
                onChangeUserName = {
                    onIntent(LoginIntent.OnChangeUserName(it))
                },
                onChangePassword = {
                    onIntent(LoginIntent.OnChangePassword(it))
                },
                onLogin = {
                    onIntent(LoginIntent.OnLogin)
                }
            )
        }
    }
}

@Composable
fun TitleAndDescriptionLogin() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
        )
        Text(
            text = "Inicio de sesión",
            style = MaterialTheme.typography.headlineMedium,

            )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
        )
        Text(
            text = "Por favor ingrese sus credenciales para continuar, y poder acceder a su cuenta.",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        )


    }
}

@Composable
fun LoginControls(
    state: LoginState,
    onChangeUserName: (String) -> Unit = {},
    onChangePassword: (String) -> Unit = {},
    onLogin: () -> Unit = {}
) {

    val buttonEnable = remember(
        state.userName,
        state.password,
        state.userNameError,
        state.passwordError

    ) {
        state.userName.isNotBlank() && state.password.isNotBlank() && state.userNameError == null && state.passwordError == null
    }
    val context = LocalContext.current
    TextFieldBase(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        value = state.userName,
        onChangeValue = onChangeUserName,
        label = stringResource(R.string.label_username)
    )
    AnimatedVisibility(state.userNameError != null) {
        Text(
            text = state.userNameError?.toString(context = context)?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(20.dp)
    )
    TextFieldBase(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
        value = state.password,
        onChangeValue = onChangePassword,
        label = stringResource(R.string.label_password)
    )

    AnimatedVisibility(state.passwordError != null){
        Text(
            text = state.passwordError?.toString(context = context)?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.error,
        )
    }

    Button(
        enabled = buttonEnable,
        onClick = onLogin,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .padding(horizontal = 10.dp)
    ) {
        Text(
            text = stringResource(R.string.label_login),
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    LoginScreen(state = LoginState(), onIntent = {})
}