package me.ruyeo.newcut.data.model

data class Rating(
    val barberShopId: Int,
    val clientId: Int,
    val feedback: String,
    val id: Int,
    val rate: Int
)