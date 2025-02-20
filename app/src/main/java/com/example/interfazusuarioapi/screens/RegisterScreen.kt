package screens

import android.widget.Space
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.interfazusuarioapi.navigation.AppScreens

@Composable
fun RegisterScreen(innerPaddingValues: PaddingValues, navController: NavController) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var calle by remember { mutableStateOf("") }
    var num by remember { mutableStateOf("") }
    var municipio by remember { mutableStateOf("") }
    var provincia by remember { mutableStateOf("") }
    var cp by remember { mutableStateOf("") }

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
            onClick = { navController.navigate(AppScreens.MainScreen.route) },
            modifier = Modifier.width(300.dp)
        ) {
            Text("Registrarse")
        }
    }


    
    


}