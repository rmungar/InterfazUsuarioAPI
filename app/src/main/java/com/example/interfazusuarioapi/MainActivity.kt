package com.example.interfazusuarioapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.interfazusuarioapi.ui.theme.InterfazUsuarioAPITheme
import kotlinx.coroutines.delay
import navigation.AppNavigation
import screens.LoginScreen
import screens.WelcomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InterfazUsuarioAPITheme {
                var showWelcomeScreen by remember { mutableStateOf(true) }

                Scaffold { innerPadding ->
                    if (showWelcomeScreen) {
                        WelcomeScreen(innerPadding)

                        LaunchedEffect(showWelcomeScreen){
                            delay(2000)
                            showWelcomeScreen = false
                        }
                    }
                    else{
                        AppNavigation(innerPadding)
                    }
                }
            }
        }
    }
}
