package com.example.interfazusuarioapi.screens

import android.os.Build
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
import androidx.compose.runtime.mutableFloatStateOf
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
import com.example.interfazusuarioapi.retrofit.API
import com.example.interfazusuarioapi.retrofit.API.Token
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
                Option2(refreshKey) {
                    refreshKey ++
                }
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
                                throw Exception("No se encontró el usuario.")
                            }
                        )
                        if (result.isSuccessful){
                            withContext(Dispatchers.Main){
                                Toast.makeText(localContext, "Tarea Creada", Toast.LENGTH_SHORT).show()
                            }
                            navController.popBackStack()
                        }
                        else{
                            withContext(Dispatchers.Main){
                                Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
                            }
                            titulo = ""
                            descripcion = ""
                            idUsuario = ""
                            fecha = ""
                            println(result.raw())
                        }
                    }
                    else{
                        CoroutineScope(Dispatchers.Main).launch{
                            Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            catch (e:Exception){
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(localContext, "No se pudo crear la tarea", Toast.LENGTH_SHORT).show()
                }
            }
        }
    ) {
        Text("Crear Tarea")
    }
}

@Composable
fun Option2(refreshKey: Int, onDelete : () -> Unit ) {
    val localContext = LocalContext.current
    val tasks = remember { mutableStateOf<List<TareaReturnDTO>>(emptyList()) }
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
        var deleted by remember { mutableStateOf(false) }
        var offsetX by remember { mutableFloatStateOf(0f) }
        val animatedOffsetX by animateFloatAsState(
            targetValue = if (deleted) if (offsetX > 0) 1000f else -1000f else offsetX,
            animationSpec = tween(durationMillis = 300),
            label = "card_slide"
        )

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
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (abs(offsetX) > 50) {

                                deleted = true
                                onDelete()
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(300) // Espera la animación
                                    val result = retrofitService.deleteTarea("Bearer " + Token, it._id)

                                    if (result.isSuccessful) {
                                        Toast.makeText(localContext, "Tarea borrada", Toast.LENGTH_SHORT).show()
                                    }
                                    else {
                                        Toast.makeText(localContext, "No se pudo borrar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                offsetX = 0f
                            }
                        }
                    ) { _, dragAmount ->
                        offsetX += dragAmount
                    }
                },
            onClick = {
                try {
                    CoroutineScope(Dispatchers.Main).launch {
                        val updateTask = retrofitService.marcarTarea(
                            "Bearer " + Token,
                            it._id
                        )

                        if (updateTask.isSuccessful && updateTask.body() != null) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(localContext, "Tarea Modificada", Toast.LENGTH_SHORT).show()
                            }

                        }
                        else{
                            withContext(Dispatchers.Main) {
                                Toast.makeText(localContext, "No se pudo modificar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                catch (e:Exception){
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(localContext, "No se pudo modificar", Toast.LENGTH_SHORT).show()
                    }
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
        Spacer(modifier = Modifier.height(10.dp))
    }
}