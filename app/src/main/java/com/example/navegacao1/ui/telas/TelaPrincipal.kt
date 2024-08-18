package com.example.navegacao1.ui.telas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navegacao1.model.dados.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, onLogoffClick: () -> Unit) {
    var scope = rememberCoroutineScope()
    val usuarios = remember { mutableStateListOf<Usuario>() }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Tela Principal")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                content = {
                    Button(onClick = { onLogoffClick() }, modifier = Modifier.fillMaxWidth()) {
                        Text("Sair")
                    }
                }
            )
        }
    )
    { innerPadding ->
        Column(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
            .padding(16.dp)) {

        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                usuarioDAO.buscar( callback = { usuariosRetornados ->
                    usuarios.clear()
                    usuarios.addAll(usuariosRetornados)
                })
            }
        }) {
            Text("Carregar")
        }

            Spacer(modifier = Modifier.height(16.dp))
        //Carrega sob demanda à medida que o usuário rola na tela
        LazyColumn {
            items(usuarios) { usuario ->
                //TODO melhore esse card. Estão colados, e com pouca informação. Deixe mais
                // elegante.
                Card( modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)) {
                    Column (modifier = Modifier.padding(16.dp)
                        .fillMaxWidth()){
                        Text(
                            text = usuario.nome,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSurface)
                    }
                }
            }
        }
    }
    }
}