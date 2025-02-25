package com.apirestsegura.ApiRestSegura2.Model

import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO
import java.util.Date


data class Tarea(
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