package me.ruyeo.newcut.data.model

data class Notification(
    val id: Int,
    val message: String,
    val orderId: Int,
    val receiverId: Int,
    val senderId: Int
)