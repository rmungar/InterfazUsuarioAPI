package com.apirestsegura.ApiRestSegura2.Model





data class Usuario(
    var _id : String?,
    var username: String,
    var password: String,
    val email: String,
    var direccion: Direccion,
    var rol: String = "USER"

){
    init {
        _id = email
    }
}