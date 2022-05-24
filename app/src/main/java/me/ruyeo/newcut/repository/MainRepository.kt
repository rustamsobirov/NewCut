package me.ruyeo.newcut.repository

import me.ruyeo.newcut.data.local.dao.BookingDao
import me.ruyeo.newcut.data.local.enitity.Booking
import me.ruyeo.newcut.data.remote.ApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val bookingDao: BookingDao
) {
    suspend fun getAllBooking() = bookingDao.getAllBooking()

    suspend fun addBooking(booking: Booking) = bookingDao.addBooking(booking)
}