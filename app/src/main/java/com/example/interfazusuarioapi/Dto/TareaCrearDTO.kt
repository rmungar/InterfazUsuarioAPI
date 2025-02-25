package com.example.interfazusuarioapi.Dto

import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO

class TareaCrearDTO(
    var _id: String? = null,
    val titulo: String,
    var estado: Boolean = false,
    val descripcion: String,
    val usuario: UsuarioDTO,

) {
    init {
        _id = "$titulo-${usuario.username}"
    }
}