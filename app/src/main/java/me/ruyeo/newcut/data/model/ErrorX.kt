package me.ruyeo.newcut.data.model

data class ErrorX(
    val code: String,
    val message: String,
    val path: String,
    val status: Int,
    val timestamp: String
)