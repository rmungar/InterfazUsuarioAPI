package com.example.interfazusuarioapi.navigation

sealed class AppScreens(val route: String) {

    object WelcomeScreen : AppScreens("WelcomeScreen")
    object RegisterScreen : AppScreens("RegisterScreen")
    object LoginScreen : AppScreens("LoginScreen")
    object MainScreen : AppScreens("MainScreen")
    object PetitionsScreen : AppScreens("PetitionsScreen")


}