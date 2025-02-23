package com.example.interfazusuarioapi.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazusuarioapi.Dto.TareaCrearDTO
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import com.example.interfazusuarioapi.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetitionsScreen(option: String, innerPaddingValues: PaddingValues, navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues)
            .verticalScroll(rememberScrollState(0)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = {
                when (option) {
                    "1" -> {Text("Crear Tarea")}
                    "2" -> {Text("Ver Tareas")}
                    "3" -> {Text("Marcar Tareas")}
                    "4" -> {Text("Eliminar Tarea")}
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.size(30.dp).padding(start = 0.dp),
                ){
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }
            },
            colors = TopAppBarColors(
                containerColor = Color.LightGray,
                scrolledContainerColor = Color.LightGray,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black,
                navigationIconContentColor = Color.Black
            )
        )
        Spacer(Modifier.height(40.dp))
        when(option){
            "1" -> {
                Option1()
            }
            "2" -> {
                Option2()
            }
            "3" -> {
                Option3()
            }
            "4" -> {
                Option4()
            }
        }
    }

}


@Composable
fun Option1() {

    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var idUsuario by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }


    OutlinedTextField(
        value = titulo,
        onValueChange = { titulo = it },
        label = { Text("Título") },
        placeholder = { Text("Título") },
        modifier = Modifier.fillMaxWidth(.8f)
    )

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
        value = descripcion,
        onValueChange = { descripcion = it },
        label = { Text("Descripción") },
        placeholder = {Text("Descripcion")},
        modifier = Modifier.fillMaxWidth(.8f)
    )

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
        value = idUsuario,
        onValueChange = { idUsuario = it },
        label = { Text("Email Usuario") },
        placeholder = {Text("Email Usuario")},
        modifier = Modifier.fillMaxWidth(.8f)
    )

    Spacer(modifier = Modifier.height(32.dp))

    OutlinedTextField(
        value = fecha,
        onValueChange = { fecha = it },
        label = { Text("Fecha Limite (yyyy-mm-dd)") },
        placeholder = {Text("Fecha Limite (yyyy-mm-dd)")},
        modifier = Modifier.fillMaxWidth(.8f)
    )

    Spacer(modifier = Modifier.height(32.dp))

    Button(
        onClick = {

            CoroutineScope(Dispatchers.IO).launch{
                // val result = retrofitService.create(
                //     TareaCrearDTO(
                //         null,
                //         titulo = titulo,
                //         descripcion = descripcion,
                //         usuario =
                //     )
                // )
            }



        }
    ) {
        Text("Crear Tarea")
    }
}
@Composable
fun Option2(){

}
@Composable
fun Option3(){

}
@Composable
fun Option4(){

}