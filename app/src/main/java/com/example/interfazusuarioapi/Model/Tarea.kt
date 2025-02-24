package com.apirestsegura.ApiRestSegura2.Model

import java.util.Date


data class Tarea(
    var _id: Int? = null,
    val titulo: String,
    var estado: Boolean = false,
    val descripcion: String,
    val usuario: Usuario,
    val fechaProgramada: Date
) {



}