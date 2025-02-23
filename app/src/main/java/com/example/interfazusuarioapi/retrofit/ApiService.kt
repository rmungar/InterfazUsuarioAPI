package com.example.interfazusuarioapi.retrofit

import com.apirestsegura.ApiRestSegura2.Dto.LoginUsuarioDTO
import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO
import com.apirestsegura.ApiRestSegura2.Dto.UsuarioRegisterDTO
import com.example.interfazusuarioapi.Dto.TareaCrearDTO
import com.example.interfazusuarioapi.Dto.TareaDTO
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {



    @POST("/usuarios/login")
    suspend fun login(
        @Body requestBody: LoginUsuarioDTO
    ): Response<TokenResponse>

    @POST("/usuarios/register")
    suspend fun register(
        @Body requestBody: UsuarioRegisterDTO
    ): Response<UsuarioDTO>

    @POST("/tareas/crear")
    suspend fun create(
        @Body requestBody: TareaCrearDTO
    ): Response<TareaDTO>

}