package me.ruyeo.newcut.data.model

data class BarberPassedOrder(
    val address: String,
    val description: String,
    val distance: Double,
    val id: Int,
    val isClosed: Boolean,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val pictures: List<String>,
    val rating: Float,
    val workingTime: String
)
