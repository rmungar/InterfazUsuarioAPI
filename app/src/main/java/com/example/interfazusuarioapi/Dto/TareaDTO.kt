package com.example.interfazusuarioapi.Dto

import com.apirestsegura.ApiRestSegura2.Model.Usuario
import java.util.Date

class TareaDTO(
    val titulo: String,
    val estado: Boolean,
    val usuario: String,
    val fechaProgramada: Date
) {
}