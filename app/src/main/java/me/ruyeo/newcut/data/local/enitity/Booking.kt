package me.ruyeo.newcut.data.local.enitity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking")
data class Booking(
    @PrimaryKey(autoGenerate = true)
    val id: Int
)
