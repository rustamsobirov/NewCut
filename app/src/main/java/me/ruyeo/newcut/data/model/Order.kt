package me.ruyeo.newcut.data.model

data class Order(
    val id: Int,
    val isAccepted: Boolean,
    val isCancelled: Boolean,
    val isReminder: Boolean,
    val reminderTime: Int
)