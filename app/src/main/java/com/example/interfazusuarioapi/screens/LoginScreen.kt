package screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.apirestsegura.ApiRestSegura2.Dto.LoginUsuarioDTO
import com.apirestsegura.ApiRestSegura2.Error.ErrorRespuesta
import com.example.interfazusuarioapi.navigation.AppScreens
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(innerPaddingValues: PaddingValues, navController: NavController) {
    val localContext = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPaddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Iniciar Sesión", style = MaterialTheme.typography.displayMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            placeholder = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            placeholder = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                try{
                    isLoading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = getLoginToken(username, password)
                        withContext(Dispatchers.Main) {
                            isLoading = false
                            if (token.isNotEmpty()) {
                                navController.navigate(AppScreens.MainScreen.route + "/${token}")
                            }
                            else {
                                println("TOKEN VACIO")
                                Toast.makeText(localContext, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                catch (e:Exception){
                    println(e)
                }

            },
            modifier = Modifier.fillMaxWidth(.7f),
            enabled = comprobarUsuarioPassword(username, password) && !isLoading
        ) {
            Text(if (isLoading) "Cargando..." else "Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                navController.navigate(AppScreens.RegisterScreen.route)
            }
        ) {
            Text("¿No tienes cuenta? Regístrate.")
        }
    }
}


fun comprobarUsuarioPassword(username: String?, password: String?): Boolean {

    if(!username.isNullOrBlank() && !password.isNullOrBlank()) {
        if (username.length < 3) return false
        if (password.length < 6) return false
        return true
    }
    else {
        return false
    }
}


suspend fun getLoginToken(username: String, password: String): String {

    try {
        val tokenResponse = retrofitService.login(
            LoginUsuarioDTO(
                username,
                password
            )
        )

        if (tokenResponse.isSuccessful) {
            print(tokenResponse.body())
            val token = tokenResponse.body() ?: ""
            return token.toString()
        }
        else{
            // Si la respuesta no es exitosa, obtenemos el código de error y el mensaje
            val errorBody = tokenResponse.errorBody()?.string() ?: "Error desconocido"
            val errorCode = tokenResponse.code()

            // Si hay un error, mostramos el código y el mensaje
            println("Código de error: $errorCode")
            println("Mensaje de error: $errorBody")
            return ""
        }
    }
    catch (e: Exception){
        println(e)
        return ""
    }
}


