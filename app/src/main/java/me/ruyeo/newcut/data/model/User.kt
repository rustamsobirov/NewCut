package me.ruyeo.newcut.data.model

data class User(
    val fullName: String,
    val id: Int,
    val organizationId: Int,
    val phoneNumber: String,
    val picturePath: String,
    val role: String
)