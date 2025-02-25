package com.example.interfazusuarioapi.screens

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazusuarioapi.Dto.TareaCrearDTO
import com.example.interfazusuarioapi.retrofit.API
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import com.example.interfazusuarioapi.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
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
                Option1(navController)
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


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Option1(navController: NavController) {
    val localContext = LocalContext.current
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

            try {
                CoroutineScope(Dispatchers.Main).launch{
                    val fechaActual = Instant.now()
                    val nuevaFecha = Date.from(fechaActual.plus(3, ChronoUnit.DAYS))
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    val usuarioDTO = retrofitService.getUsuario("Bearer " + API.Token, idUsuario)
                    if (usuarioDTO.isSuccessful){
                        val result = retrofitService.create( "Bearer " + API.Token,
                            if (usuarioDTO.body() != null){
                                TareaCrearDTO(
                                    _id = null,
                                    titulo = titulo,
                                    estado = false,
                                    descripcion = descripcion,
                                    usuario = usuarioDTO.body()!!,
                                    fechaProgramada = try {
                                        formatter.parse(fecha)
                                    }
                                    catch (e:Exception){
                                        nuevaFecha
                                    }!!
                                )
                            }
                            else{
                                throw Exception("ME CAGO EN LA PUTA")
                            }
                        )
                        if (result.isSuccessful){
                            Toast.makeText(localContext, "Tarea Creada", Toast.LENGTH_SHORT).show()
                            navController.popBackStack()
                        }
                        else{
                            Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
                            titulo = ""
                            descripcion = ""
                            idUsuario = ""
                            fecha = ""
                        }
                    }
                    else{
                        println(usuarioDTO.errorBody())
                    }
                }
            }
            catch (e:Exception){
                println(e)
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
