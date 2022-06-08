package me.ruyeo.newcut.data.model

data class Barbershop(
    val address: String,
    val description: String,
    val id: Int,
    val isClosed: Boolean,
    val latitude: String,
    val longitude: String,
    val name: String,
    val workingTime: String
)