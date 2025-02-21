package com.example.interfazusuarioapi.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {

    private const val BASE_URL = "https://proyectofinalapiinterfaz.onrender.com"

    val retrofitService: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }


    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS) // Tiempo de espera al conectar
        .readTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para leer la respuesta
        .writeTimeout(30, TimeUnit.SECONDS) // Tiempo de espera para escribir la petición
        .build()


    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

}