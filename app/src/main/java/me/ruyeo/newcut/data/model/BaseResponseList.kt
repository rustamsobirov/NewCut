package me.ruyeo.newcut.data.model

data class BaseResponseList<T>(
    val data: List<T>,
    val error: Error,
    val success: Boolean
)