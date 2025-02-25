package com.example.interfazusuarioapi.Dto

import com.apirestsegura.ApiRestSegura2.Dto.UsuarioDTO
import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

class TareaCrearDTO(
    var _id: Int? = null,
    val titulo: String,
    var estado: Boolean = false,
    val descripcion: String,
    val usuario: UsuarioDTO,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    val fechaProgramada: String
) {

}