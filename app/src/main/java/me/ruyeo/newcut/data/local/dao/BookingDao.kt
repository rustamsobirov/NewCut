package me.ruyeo.newcut.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.ruyeo.newcut.data.local.enitity.Booking

@Dao
interface BookingDao {
    @Insert()
    suspend fun addBooking(booking: Booking)

    @Query("SELECT * FROM booking")
    suspend fun getAllBooking(): List<Booking>
}