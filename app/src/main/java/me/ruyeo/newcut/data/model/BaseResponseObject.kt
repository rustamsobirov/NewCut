package me.ruyeo.newcut.data.model

data class BaseResponseObject<T>(
    val data: T,
    val error: Error,
    val success: Boolean
)