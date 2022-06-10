package me.ruyeo.newcut.data.model

data class UserResponseUpdate(
    val `data`: Boolean,
    val error: ErrorX,
    val status: Int,
    val success: Boolean,
    val totalCount: Int
)