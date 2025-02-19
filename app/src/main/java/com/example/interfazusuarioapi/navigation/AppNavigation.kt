package navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.interfazusuarioapi.navigation.AppScreens
import screens.LoginScreen
import screens.RegisterScreen
import screens.WelcomeScreen


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

    }
}