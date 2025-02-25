package com.example.interfazusuarioapi.screens

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apirestsegura.ApiRestSegura2.Dto.TareaReturnDTO
import com.example.interfazusuarioapi.Dto.TareaCrearDTO
import com.example.interfazusuarioapi.Dto.TareaDTO
import com.example.interfazusuarioapi.navigation.AppScreens
import com.example.interfazusuarioapi.retrofit.API
import com.example.interfazusuarioapi.retrofit.API.Token
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetitionsScreen(option: String, innerPaddingValues: PaddingValues, navController: NavController){
    var refreshKey by remember { mutableStateOf(0) }

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
                }
            },
            actions = {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .size(30.dp)
                        .padding(start = 0.dp),
                ){
                    Icon(Icons.Default.ArrowBack, contentDescription = null)
                }

                if (option == "2"){
                    IconButton(
                        onClick = {
                            refreshKey++
                        },
                        modifier = Modifier
                            .size(30.dp)
                            .padding(start = 0.dp),
                    ){
                        Icon(Icons.Default.Refresh, contentDescription = null)
                    }
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
                Option2(refreshKey)
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

    Spacer(modifier = Modifier.height(64.dp))

    Button(
        onClick = {
            try {
                CoroutineScope(Dispatchers.Main).launch{
                    val usuarioDTO = retrofitService.getUsuario("Bearer " + API.Token, idUsuario)
                    if (usuarioDTO.isSuccessful){
                        val result = retrofitService.create( "Bearer " + API.Token,
                            if (usuarioDTO.body() != null){
                                TareaCrearDTO(
                                    _id = null,
                                    titulo = titulo,
                                    estado = false,
                                    descripcion = descripcion,
                                    usuario = usuarioDTO.body()!!
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
                            println(result.raw())
                        }
                    }
                    else{
                        Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            catch (e:Exception){
                Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
            }
        }
    ) {
        Text("Crear Tarea")
    }
}

@Composable
fun Option2(refreshKey: Int) {
    val localContext = LocalContext.current
    val tasks = remember { mutableStateOf<List<TareaReturnDTO>>(emptyList()) }
    var offsetX by remember { mutableStateOf(0f) }
    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = tween(durationMillis = 300)
    )
    // Lanza la llamada a la API solo una vez, utilizando LaunchedEffect
    LaunchedEffect(refreshKey) {
        val tareas = retrofitService.getTareas("Bearer " + API.Token)
        if (tareas.isSuccessful && tareas.body() != null) {
            // Actualiza el estado con el resultado de la llamada
            tasks.value = tareas.body()!!
        } else {
            // Manejo de errores si la llamada falla
            println(tareas.raw())
        }
    }

    // Mostrar las tareas
    tasks.value.forEach {
        ElevatedCard(
            modifier = Modifier
                .offset { IntOffset(animatedOffsetX.roundToInt(), 0) }
                .fillMaxWidth(0.7f)
                .height(60.dp)
                .border(
                    width = 2.dp,
                    color = if (it.estado) Color.Green else Color.Red,
                    shape = RoundedCornerShape(20)
                )
                .padding(vertical = 10.dp)
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (abs(offsetX) > 400) {
                                try {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        val result = retrofitService.deleteTarea(
                                            "Bearer " + Token,
                                            it._id
                                        )

                                        if (result.isSuccessful){
                                            Toast.makeText(localContext, "Tarea borrada", Toast.LENGTH_SHORT).show()
                                        }
                                        else{
                                            Toast.makeText(localContext, "No se pudo borrar", Toast.LENGTH_SHORT).show()
                                            println(result.raw())
                                        }
                                    }
                                }
                                catch (e:Exception){
                                    println(e)
                                }
                                // Llamamos a la función para eliminar la tarea
                            } else {
                                offsetX = 0f // Si no se supera el umbral, regresa a su posición
                            }
                        }
                    ) { _, dragAmount ->
                        offsetX += dragAmount
                    }
                },
            onClick = {
                try {
                    CoroutineScope(Dispatchers.Main).launch {

                        val idTarea = retrofitService.getIdByData(
                            "Bearer " + Token,
                            tareaDTO = TareaDTO(
                                titulo = it.titulo,
                                estado = it.estado,
                                usuario = it.usuario,

                            )
                        )

                        if (idTarea.isSuccessful && idTarea.body() != null){

                            val updateTask = retrofitService.marcarTarea(
                                "Bearer " + Token,
                                idTarea.body()!!
                            )

                            if (updateTask.isSuccessful && updateTask.body() != null) {
                                it.estado = updateTask.body()!!.estado
                                Toast.makeText(localContext, "Tarea Modificada", Toast.LENGTH_SHORT).show()

                            }
                            else{
                                Toast.makeText(localContext, "No se pudo modificar", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(localContext, "La tarea no existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e:Exception){
                    Toast.makeText(localContext, "No se pudo modificar", Toast.LENGTH_SHORT).show()
                }

            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("${it.titulo} -> ${if (it.estado) "Completada" else "En proceso"} -> ${it.usuario}")
            }

        }
    }
}