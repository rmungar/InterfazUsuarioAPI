package com.example.interfazusuarioapi.Dto

import com.apirestsegura.ApiRestSegura2.Model.Usuario
import java.util.Date

class TareaCrearDTO(
    var _id: String? = null,
    val titulo: String,
    var estado: Boolean = false,
    val descripcion: String,
    val usuario: Usuario,
    val fechaProgramada: Date
) {

}