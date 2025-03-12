package com.example.interfazusuarioapi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazusuarioapi.navigation.AppScreens
import com.example.interfazusuarioapi.retrofit.API

/**
 * Pantalla principal de la aplicacion, en la que se mostraran distintas opciones para que el usuario seleccione la que quiera.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(token: String, innerPaddingValues: PaddingValues, navController: NavController){

    API.settoken(token)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(0))
            .padding(innerPaddingValues),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopAppBar(
           title = { Text("Inicio") },
           actions = {
               Icon(Icons.Default.Menu, contentDescription = null, modifier = Modifier
                   .size(30.dp)
                   .padding(start = 0.dp)
                   .clickable { navController.popBackStack() }
               )
           },
           colors = TopAppBarColors(
               containerColor = Color.LightGray,
               scrolledContainerColor = Color.LightGray,
               titleContentColor = Color.Black,
               actionIconContentColor = Color.Black,
               navigationIconContentColor = Color.Black
           )
        )
        Spacer(Modifier.height(20.dp))
        Divider(true)
        Card(
            modifier = Modifier.fillMaxWidth(.8f).height(70.dp).padding(bottom = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Actualizar Usuario")
                Spacer(Modifier.fillMaxWidth(.6f))
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = null
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(.8f).height(70.dp).padding(bottom = 15.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Eliminar Usuario")
                Spacer(Modifier.fillMaxWidth(.6f))
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = null
                )
            }
        }
        Divider(false)
        Card(
            modifier = Modifier.fillMaxWidth(.8f).height(70.dp).padding(bottom = 15.dp),
            onClick = {
                navController.navigate(AppScreens.PetitionsScreen.route + "/1")
            }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Crear Tarea")
                Spacer(Modifier.fillMaxWidth(.7f))
                Icon(
                    imageVector = Icons.Default.ArrowForward, contentDescription = null
                )
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(.8f).height(70.dp).padding(bottom = 15.dp),
            onClick = {
                navController.navigate(AppScreens.PetitionsScreen.route + "/2")
            }
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text("Ver Tareas")
                Spacer(Modifier.fillMaxWidth(.7f))
                Icon(
                    imageVector = Icons.Default.ArrowForward, contentDescription = null
                )
            }
        }
    }
}

@Composable
fun Divider(user: Boolean){
    Row(
        Modifier.fillMaxWidth().padding(vertical = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(.46f)
        )
        Icon(
            imageVector = if (user) {
                Icons.Default.AccountCircle
            }
            else{
                Icons.Default.List
            },
            contentDescription = null
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth()
        )
    }
}