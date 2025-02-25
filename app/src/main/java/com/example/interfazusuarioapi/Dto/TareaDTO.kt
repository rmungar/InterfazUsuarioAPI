package com.example.interfazusuarioapi.Dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.util.Date

class TareaDTO(
    val titulo: String,
    var estado: Boolean,
    val usuario: String,

) {
}