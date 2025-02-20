package com.example.interfazusuarioapi.retrofit

import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @GET("usuarios/login")
    fun login(): Response<String>

    @POST
    fun register(): Response<UsuarioDTO>



}