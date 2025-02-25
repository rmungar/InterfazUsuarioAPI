package com.apirestsegura.ApiRestSegura2.Dto

data class TareaReturnDTO(
    val _id: Int,
    val titulo: String,
    var estado: Boolean,
    val usuario: String
) {
}