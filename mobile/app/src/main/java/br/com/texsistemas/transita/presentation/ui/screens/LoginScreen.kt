package br.com.texsistemas.transita.presentation.ui.screens

import br.com.texsistemas.transita.R

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.texsistemas.transita.presentation.ui.components.global.TransitaButton
import br.com.texsistemas.transita.presentation.ui.theme.ColorError
import br.com.texsistemas.transita.presentation.ui.theme.Gray400
import br.com.texsistemas.transita.presentation.ui.theme.Gray500
import br.com.texsistemas.transita.presentation.ui.theme.Gray600
import br.com.texsistemas.transita.presentation.ui.theme.GreenBase
import br.com.texsistemas.transita.presentation.ui.theme.Typography
import br.com.texsistemas.transita.presentation.ui.theme.White
import br.com.texsistemas.transita.presentation.viewmodel.login.LoginUiEvent
import br.com.texsistemas.transita.presentation.viewmodel.login.LoginUiState

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEvent: (LoginUiEvent) -> Unit,
    onNavigateToHome: () -> Unit
) {
    val senhaVisible = remember { mutableStateOf(false) }

    if (uiState.isCarregando) {
        onNavigateToHome()
    }

    Box(
        modifier = modifier
            .background(White)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(32.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(240.dp),
                    painter = painterResource(R.drawable.logo_text_green),
                    contentDescription = stringResource(R.string.content_description_logo)
                )
            }

            Text(
                text = stringResource(R.string.label_email),
                color = Gray600,
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onEvent(LoginUiEvent.OnEmailChanged(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.placeholder_email),
                        color = Gray500
                    )
                },
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Gray500,
                    unfocusedTextColor = Gray500,
                    cursorColor = GreenBase,
                    focusedIndicatorColor = GreenBase,
                    unfocusedIndicatorColor = GreenBase,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
            )

            Text(
                text = stringResource(R.string.label_senha),
                color = Gray600,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            OutlinedTextField(
                value = uiState.senha,
                onValueChange = { onEvent(LoginUiEvent.OnSenhaChanged(it)) },
                label = {
                    Text(
                        text = stringResource(R.string.placeholder_senha),
                        color = Gray500
                    )
                },
                singleLine = true,
                visualTransformation = if (senhaVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Gray500,
                    unfocusedTextColor = Gray500,
                    cursorColor = GreenBase,
                    focusedIndicatorColor = GreenBase,
                    unfocusedIndicatorColor = GreenBase,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                trailingIcon = {
                    val icon =
                        if (senhaVisible.value) R.drawable.icon_visible else R.drawable.icon_notvisible
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = stringResource(R.string.content_description_icone_visibilidade),
                        modifier = Modifier
                            .size(30.dp)
                            .padding(4.dp)
                            .background(Color.Transparent)
                            .clickable { senhaVisible.value = !senhaVisible.value }
                    )
                }

            )

            if (uiState.mensagemErro != null) {
                Text(
                    text = uiState.mensagemErro,
                    style = Typography.bodySmall,
                    color = ColorError,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            TransitaButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                text = stringResource(R.string.label_button_entrar),
                onClick = {
                    onEvent(LoginUiEvent.OnLoginClicked(uiState.email, uiState.senha))
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.tex_sistemas_2024),
                style = Typography.bodySmall,
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .align(Alignment.CenterHorizontally),
                color = Gray400
            )
        }
    }

}

@Preview(name = "Light Mode")
//@Preview(name = "Dark Mode", uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    LoginScreen(onNavigateToHome = {}, uiState = LoginUiState(), onEvent = {})
}

