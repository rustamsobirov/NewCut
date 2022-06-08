package me.ruyeo.newcut.repository

import me.ruyeo.newcut.model.map.Latlng
import me.ruyeo.newcut.data.local.dao.BookingDao
import me.ruyeo.newcut.data.local.enitity.Booking
import me.ruyeo.newcut.data.remote.ApiService
import me.ruyeo.newcut.data.remote.GeoService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiService: ApiService,
    private val geoService: GeoService,
    private val bookingDao: BookingDao
) {
    suspend fun getLocationName(latLng: Latlng) = geoService.getGeoCodeInfo(query = latLng)


    //orders
    suspend fun getAllOrders() = apiService.getAllOrders()


    suspend fun getAllBooking() = bookingDao.getAllBooking()

    suspend fun addBooking(booking: Booking) = bookingDao.addBooking(booking)
}