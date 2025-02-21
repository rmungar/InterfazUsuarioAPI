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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.apirestsegura.ApiRestSegura2.Dto.UsuarioRegisterDTO
import com.apirestsegura.ApiRestSegura2.Model.Direccion
import com.example.interfazusuarioapi.navigation.AppScreens
import com.example.interfazusuarioapi.retrofit.API.retrofitService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(innerPaddingValues: PaddingValues, navController: NavController) {
    val localContext = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState(0))
            .padding(innerPaddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Spacer(modifier = Modifier.height(60.dp))

        Text("Registrarse", style = MaterialTheme.typography.displayMedium)

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
            placeholder = {Text("Contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = passwordRepeat,
            onValueChange = { passwordRepeat = it },
            label = { Text("Repita la contraseña") },
            placeholder = {Text("Contraseña")},
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            placeholder = {Text("Email")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = calle,
            onValueChange = { calle = it },
            label = { Text("Calle") },
            placeholder = {Text("Calle")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = num,
            onValueChange = { num = it },
            label = { Text("Número") },
            placeholder = {Text("Número")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = municipio,
            onValueChange = { municipio = it },
            label = { Text("Municipio") },
            placeholder = {Text("Municipio")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = provincia,
            onValueChange = { provincia = it },
            label = { Text("Provincia") },
            placeholder = {Text("Provincia")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cp,
            onValueChange = { cp = it },
            label = { Text("Código Postal") },
            placeholder = {Text("Código Postal")},
            modifier = Modifier.fillMaxWidth(.8f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                try{
                    isLoading = true
                    CoroutineScope(Dispatchers.IO).launch {
                        val result = getRegisterResponse(username,password,passwordRepeat,email,calle,num,municipio,provincia,cp)
                        withContext(Dispatchers.Main) {
                            isLoading = false
                            if (result) {
                                Toast.makeText(localContext, "Registro exitoso", Toast.LENGTH_SHORT).show()
                                username = ""
                                password = ""
                                passwordRepeat = ""
                                email = ""
                                calle = ""
                                municipio = ""
                                cp = ""
                                provincia = ""
                                num = ""
                            }
                            else {
                                Toast.makeText(localContext, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                catch (e:Exception) {
                    println(e)
                }

            },
            modifier = Modifier.width(300.dp),
            enabled = checkData(username,password,passwordRepeat,email,calle,num,municipio,provincia,cp)
        ) {
            Text("Registrarse")
        }
    }
}

suspend fun getRegisterResponse(username: String, password: String, passwordRepeat: String, email: String, calle: String, numero: String, municipio: String, provincia: String, codigoPostal: String): Boolean{

    try {
        val usuarioResponse = retrofitService.register(
            UsuarioRegisterDTO(
                username,
                email,
                password,
                passwordRepeat,
                Direccion(
                    calle,
                    numero,
                    municipio,
                    provincia,
                    codigoPostal
                ),
                "USER"
            )
        )

        if (usuarioResponse.isSuccessful){
            val usuarioResponseDTO = usuarioResponse.body()

            if (usuarioResponseDTO != null){
                return true
            }
            else{
                return false
            }
        }
        else{
            val errorBody = usuarioResponse.errorBody()?.string() ?: "Error desconocido"
            val errorCode = usuarioResponse.code()

            // Si hay un error, mostramos el código y el mensaje
            println("Código de error: $errorCode")
            println("Mensaje de error: $errorBody")
            return false
        }
    }
    catch (e:Exception){
        println(e)
        return false
    }
}

fun checkData(username: String, password: String, passwordRepeat: String, email: String, calle: String, numero: String, municipio: String, provincia: String, codigoPostal: String): Boolean {
    if (username.isEmpty() || password.isEmpty() || passwordRepeat.isEmpty() || email.isEmpty() || calle.isEmpty() || numero.isEmpty() || municipio.isEmpty() || provincia.isEmpty() || codigoPostal.isEmpty()) {
        return false
    }
    else {
        return true
    }
}