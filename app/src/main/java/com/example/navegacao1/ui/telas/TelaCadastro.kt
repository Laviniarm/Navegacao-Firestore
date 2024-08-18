package com.example.navegacao1.ui.telas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navegacao1.model.dados.Usuario
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TelaCadastro(modifier: Modifier = Modifier, onSignUpClick: () -> Unit) {
    var scope = rememberCoroutineScope()

    var login by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }
    var mensagem by remember { mutableStateOf<String?>(null) }

    var deveNavegar by remember { mutableStateOf(false) }


    LaunchedEffect(deveNavegar) {
        if (deveNavegar) {
            delay(3000)
            onSignUpClick()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Cadastre-se",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        TextField(
            value = login, onValueChange = { login = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = senha, onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (login.isNotBlank() && senha.isNotBlank()) {
                if (login == senha) {
                    scope.launch {
                        val usuario = Usuario(nome = login, senha = senha)
                        usuarioDAO.adicionar(usuario) {
                            if (it != null) {
                                mensagem = "Usuario cadastrado com sucesso!"
                                deveNavegar = true
                            } else {
                                mensagemErro = "Login ou senha inválidos!"
                            }
                        }
                    }
                } else {
                    mensagemErro = "Login e senha devem ser iguais!"
                }
            } else {
                mensagemErro = "Nome e senha não podem estar vazios!"
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Cadastrar", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        mensagemErro?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        mensagem?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}