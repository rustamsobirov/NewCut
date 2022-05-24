package me.ruyeo.newcut.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ruyeo.newcut.data.local.dao.BookingDao
import me.ruyeo.newcut.data.local.enitity.Booking

@Database(entities = [Booking::class], version = 1,exportSchema = true)
abstract class BookingDatabase : RoomDatabase() {
    abstract fun getBookingDao(): BookingDao
}