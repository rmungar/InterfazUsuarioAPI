package com.example.interfazusuarioapi.retrofit

import com.apirestsegura.ApiRestSegura2.Dto.LoginUsuarioDTO
import com.apirestsegura.ApiRestSegura2.Dto.TareaReturnDTO
import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO
import com.apirestsegura.ApiRestSegura2.Dto.UsuarioRegisterDTO
import com.apirestsegura.ApiRestSegura2.Model.Tarea
import com.example.interfazusuarioapi.Dto.TareaCrearDTO
import com.example.interfazusuarioapi.Dto.TareaDTO
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

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
        @Header("Authorization") token: String,
        @Body requestBody: TareaCrearDTO
    ): Response<TareaReturnDTO>


    @GET("/usuarios/usuario/{id}")
    suspend fun getUsuario(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<UsuarioDTO>

    @GET("/tareas/obtener")
    suspend fun getTareas(
        @Header("Authorization") token: String
    ): Response<List<TareaReturnDTO>>


    @POST("/tareas/obtenerId")
    suspend fun getIdByData(
        @Header("Authorization") token: String,
        @Body tareaDTO: TareaDTO
    ): Response<String>


    @PUT("/tareas/marcar/{idTarea}")
    suspend fun marcarTarea(
        @Header("Authorization") token: String,
        @Path("idTarea") tareaID: String
    ): Response<TareaReturnDTO>


    @DELETE("/tareas/eliminar/{idTarea}")
    suspend fun deleteTarea(
        @Header("Authorization") token: String,
        @Path("idTarea") tareaID: String
    ): Response<ResponseBody>

}