package navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interfazusuarioapi.navigation.AppScreens
import com.example.interfazusuarioapi.screens.MainScreen
import com.example.interfazusuarioapi.screens.PetitionsScreen
import screens.LoginScreen
import screens.RegisterScreen
import screens.WelcomeScreen

/**
 * Navegación de la aplicación
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(innerPaddingValues: PaddingValues) {
    val navControlador = rememberNavController()

    NavHost(navControlador, AppScreens.LoginScreen.route) {

        composable(AppScreens.LoginScreen.route) {
            LoginScreen(innerPaddingValues, navControlador)
        }

        composable(AppScreens.RegisterScreen.route) {
            RegisterScreen(innerPaddingValues, navControlador)
        }

        composable(AppScreens.MainScreen.route + "/{token}",
            arguments = listOf(navArgument("token"){type = NavType.StringType})
        ) {
            val token = it.arguments?.getString("token") ?: ""
            MainScreen(token, innerPaddingValues, navControlador)
        }

        composable(AppScreens.PetitionsScreen.route + "/{option}",
            arguments = listOf(navArgument("option"){type = NavType.StringType})
        ) {
            val option = it.arguments?.getString("option") ?: ""
            PetitionsScreen(option, innerPaddingValues, navControlador)
        }

    }
}